CREATE TABLE client (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    contact_email VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE ticket (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    application VARCHAR(100) NOT NULL,
    title  VARCHAR(120) NOT NULL,
    description TEXT NOT NULL,
    steps_to_reproduce TEXT,
    impact TEXT,
    priority VARCHAR(2) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'NEW',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    due_at TIMESTAMPTZ,
    resolved_at TIMESTAMPTZ,

    CONSTRAINT fk_ticket_client
        FOREIGN KEY (client_id) REFERENCES client (id),
    CONSTRAINT ck_ticket_priority
        CHECK (priority IN ('P1', 'P2', 'P3', 'P4')),
    CONSTRAINT ck_ticket_status
        CHECK (status IN ('NEW', 'IN_PROGRESS', 'WAITING_CLIENT', 'RESOLVED', 'CLOSED'))
);
CREATE TABLE comment (
    id BIGSERIAL PRIMARY KEY ,
    ticket_id BIGINT NOT NULL ,
    CONSTRAINT fk_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id),
    CONSTRAINT ck_comment_body_length CHECK (char_length(body) BETWEEN 1 AND 2000),
    author VARCHAR(100) NOT NULL,
    body TEXT NOT NULL ,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ticket_status_priority ON ticket (status, priority);
CREATE INDEX idx_ticket_client_created ON ticket (client_id, created_at);
CREATE INDEX idx_comment_ticket ON comment (ticket_id);

CREATE VIEW v_overdue_ticket AS
SELECT
    t.id,
    t.title,
    t.priority,
    t.status,
    t.due_at,
    c.name AS client_name
FROM ticket t
JOIN client c ON t.client_id = c.id
WHERE
    t.due_at IS NOT NULL
    AND t.due_at < NOW()
    AND t.status NOT IN ('RESOLVED', 'CLOSED');