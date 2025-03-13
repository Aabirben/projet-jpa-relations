# projet-jpa-relations

Instructions d’utilisation

 Utilisation de l’application :
   - Un menu interactif s’affiche dans la console.
   - Choisissez une relation (1-8) ou quittez (9).
   - Sélectionnez une opération CRUD (1: Create, 2: Read, 3: Update, 4: Delete).
   - Choisissez l’entité sur laquelle agir (1: Student, 2: Course).
   - Suivez les instructions pour entrer des IDs ou confirmer les actions.
   - Note : Dans les relations unidirectionnelles, agir sur `Course` pour modifier `Student` affiche un message expliquant que c’est impossible (ex. : "Course n’a pas de référence à Student").

 Détails techniques

- Classe `Main`:
  - Supporte les opérations CRUD dans les deux directions pour les relations bidirectionnelles.
  - Affiche des messages explicatifs pour les cas impossibles dans les relations unidirectionnelles.

Jointures dans chaque cas:

-One-to-One Uni : Jointure via @JoinColumn(name = "course_id") dans Student.
-One-to-One Bi : Jointure via @JoinColumn(name = "course_id") dans Student, bidirectionnelle avec mappedBy dans Course.
-One-to-Many Uni : Jointure via @JoinColumn(name = "student_id") dans Course (côté Many).
-One-to-Many Bi : Jointure via @JoinColumn(name = "student_id") dans Course, bidirectionnelle avec mappedBy dans Student.
-Many-to-One Uni : Jointure via @JoinColumn(name = "course_id") dans Student.
-Many-to-One Bi : Jointure via @JoinColumn(name = "course_id") dans Student, bidirectionnelle avec mappedBy dans Course.
-Many-to-Many Uni (explicite) : Jointure via entité StudentCourse, unidirectionnelle depuis Student.
-Many-to-Many Bi (explicite) : Jointure via entité StudentCourse, bidirectionnelle avec mappedBy dans Course.
