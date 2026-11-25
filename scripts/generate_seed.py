import random
from datetime import datetime, timedelta, timezone
#datetime : représente une date et une heure présise 
#timedelta : ajouter ou soustraire une durée à une date
#timezone : gerer les fuseaux horaires


random.seed(42)  # même résultat à chaque exécution

CLIENTS = ["Atlas Distribution", "Fes Logistique", "Maroc Santé Plus"]
APPLICATIONS = ["CRM", "Facturation", "Portail client", "Application mobile"]
DEADLINE_HOURS = {"P1": 8, "P2": 48, "P3": 120, "P4": None}
PRIORITIES = ["P1", "P2", "P3", "P4"]
PRIORITY_WEIGHTS = [10, 25, 45, 20]
STATUSES = ["NEW", "IN_PROGRESS", "WAITING_CLIENT", "RESOLVED", "CLOSED"]
STATUS_WEIGHTS = [10, 15, 10, 30, 35]

TITLES = [
    "Erreur à l'ouverture de la page", "Lenteur lors de la recherche",
    "Export impossible", "Message d'erreur inattendu",
    "Bouton sans effet", "Demande d'un nouveau rapport",
]

#convertir une valeur Python en texte compatible avec une instruction sql
def sql_text(value):
    """Protège les apostrophes et gère NULL."""
    if value is None:
        return "NULL"
    return "'" + value.replace("'", "''") + "'"

#convertir une date python en texte compatible avec sql
def sql_date(value):
    return "NULL" if value is None else "'" + value.isoformat() + "'" #isoformat -> transforme une date python en chaîne de caractères au format ISO 8601


def main():
    end = datetime.now(timezone.utc)
    start = end - timedelta(days=90)
    lines = ["-- Données fictives générées par scripts/generate_seed.py", ""]

    # 1. Les clients (sans doublon si on recharge le fichier)
    for name in CLIENTS:
        email = name.lower().replace(" ", ".") + "@exemple.ma"
        lines.append(
            f"INSERT INTO client (name, contact_email) VALUES "
            f"({sql_text(name)}, {sql_text(email)}) ON CONFLICT (name) DO NOTHING;" 
        )
    lines.append("")

    # 2. Les tickets
    for i in range(1, 121):
        client = random.choice(CLIENTS)
        priority = random.choices(PRIORITIES, PRIORITY_WEIGHTS)[0]
        status = random.choices(STATUSES, STATUS_WEIGHTS)[0]
        created = start + timedelta(seconds=random.randint(0, 90 * 24 * 3600))

        hours = DEADLINE_HOURS[priority]
        due = created + timedelta(hours=hours) if hours else None

        resolved = None
        if status in ("RESOLVED", "CLOSED"):
            base = hours if hours else 72
            resolved = created + timedelta(hours=random.uniform(1, base * 1.5))

        impact = "Utilisateurs bloqués" if priority in ("P1", "P2") else None
        title = f"{random.choice(TITLES)} #{i}"

        lines.append(
            "INSERT INTO ticket (client_id, application, title, description, "
            "impact, priority, status, created_at, due_at, resolved_at) VALUES ("
            f"(SELECT id FROM client WHERE name = {sql_text(client)}), "
            f"{sql_text(random.choice(APPLICATIONS))}, {sql_text(title)}, "
            f"{sql_text('Description fictive du ticket ' + str(i))}, "
            f"{sql_text(impact)}, '{priority}', '{status}', "
            f"{sql_date(created)}, {sql_date(due)}, {sql_date(resolved)});"
        )

    with open("sql/seed_tickets.sql", "w", encoding="utf-8") as f:
        f.write("\n".join(lines) + "\n")
    print("120 tickets écrits dans sql/seed_tickets.sql")


main()
#ON CONFLICT (name) DO NOTHING : Si un client avec le meme nom existe deja, ne fais rien