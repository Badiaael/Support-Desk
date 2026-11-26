package com.example.SupportDesk.ticket;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public enum Priority{
    P1(8),
    P2(48),
    P3(120),
    P4(null);
    private final Integer deadlineHours;

    Priority(Integer deadlineHours) {
        this.deadlineHours = deadlineHours;
    }

    public Optional<Duration> deadline() {
        return Optional.ofNullable(deadlineHours).map(Duration::ofHours);
    }// Si le nombre d'heures existe on le transforme en "Duration" sinon le resultat reste vide

    public Optional<Instant> dueAt(Instant createdAt) {
        return deadline().map(createdAt::plus); // date de création plus deadline <- l'equivalant de d -> createdAt.plus(d)
    }

    public boolean requiresImpact() {
        return this == P1 || this == P2;
    }
}

