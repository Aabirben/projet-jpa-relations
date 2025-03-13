package com.example.projetjparelations;

import com.example.projetjparelations.many_to_many_uni.Student;
import com.example.projetjparelations.one_to_one_uni.*;
import com.example.projetjparelations.one_to_one_bi.*;
import com.example.projetjparelations.one_to_many_uni.*;
import com.example.projetjparelations.one_to_many_bi.*;
import com.example.projetjparelations.many_to_one_uni.*;
import com.example.projetjparelations.many_to_one_bi.*;
import com.example.projetjparelations.many_to_many.*;
import com.example.projetjparelations.many_to_many_uni.*;
import com.example.projetjparelations.many_to_many_bi.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentCoursePU");
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Décommentez les entités de persistence.xml correspondantes à votre choix de cardinalités de relation.");
        while (true) {
            displayMenu();
            int choice = getUserChoice(1, 10);
            if (choice == 10) break;

            System.out.println("\nChoisissez une action CRUD :");
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            int crudChoice = getUserChoice(1, 4);

            System.out.println("\nAgir sur :");
            System.out.println("1. Student");
            System.out.println("2. Course");
            int entityChoice = getUserChoice(1, 2);

            executeAction(choice, crudChoice, entityChoice);
        }
        emf.close();
        scanner.close();
        System.out.println("Programme terminé.");
    }

    private static void displayMenu() {
        System.out.println("\n=== Menu des relations JPA ===");
        System.out.println("1. One-to-One Unidirectionnel");
        System.out.println("2. One-to-One Bidirectionnel");
        System.out.println("3. One-to-Many Unidirectionnel");
        System.out.println("4. One-to-Many Bidirectionnel");
        System.out.println("5. Many-to-One Unidirectionnel");
        System.out.println("6. Many-to-One Bidirectionnel");
        System.out.println("7. Many-to-Many Unidirectionnel (avec table explicite)");
        System.out.println("8. Many-to-Many Bidirectionnel (avec table explicite)");
        System.out.println("9. Quitter");
        System.out.print("Entrez votre choix (1-9) : ");
    }

    private static int getUserChoice(int min, int max) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) break;
                System.out.print("Choix invalide. Entrez un nombre entre " + min + " et " + max + " : ");
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide. Entrez un nombre : ");
            }
        }
        return choice;
    }

    private static void executeAction(int relationChoice, int crudChoice, int entityChoice) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            switch (relationChoice) {
                case 1: handleOneToOneUni(em, crudChoice, entityChoice); break;
                case 2: handleOneToOneBi(em, crudChoice, entityChoice); break;
                case 3: handleOneToManyUni(em, crudChoice, entityChoice); break;
                case 4: handleOneToManyBi(em, crudChoice, entityChoice); break;
                case 5: handleManyToOneUni(em, crudChoice, entityChoice); break;
                case 6: handleManyToOneBi(em, crudChoice, entityChoice); break;
                case 7: handleManyToManyUni(em, crudChoice, entityChoice); break;
                case 8: handleManyToManyBi(em, crudChoice, entityChoice); break;
            }
            tx.commit();
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            if (tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void handleOneToOneUni(EntityManager em, int crudChoice, int entityChoice) {
        System.out.println("\n=== One-to-One Unidirectionnel ===");
        if (entityChoice == 1) { // Depuis Student
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.one_to_one_uni.Student student = new com.example.projetjparelations.one_to_one_uni.Student("Alice");
                    student.setCourse(new com.example.projetjparelations.one_to_one_uni.Course("Math"));
                    em.persist(student);
                    System.out.println("Créé : " + student);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Student à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_one_uni.Student found = em.find(com.example.projetjparelations.one_to_one_uni.Student.class, id);
                    System.out.println("Lu : " + (found != null ? found : "Non trouvé"));
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Student à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_one_uni.Student toUpdate = em.find(com.example.projetjparelations.one_to_one_uni.Student.class, idToUpdate);
                    if (toUpdate != null) {
                        toUpdate.getCourse().setTitle("Advanced Math");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
                case 4: // Delete
                    System.out.print("Entrez l'ID du Student à supprimer : ");
                    Long idToDelete = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_one_uni.Student toDelete = em.find(com.example.projetjparelations.one_to_one_uni.Student.class, idToDelete);
                    if (toDelete != null) {
                        em.remove(toDelete);
                        System.out.println("Supprimé : " + toDelete);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
            }
        } else { // Depuis Course
            System.out.println("Action impossible : Cette relation est unidirectionnelle. Course n’a pas de référence à Student.");
        }
    }

    private static void handleOneToOneBi(EntityManager em, int crudChoice, int entityChoice) {
        System.out.println("\n=== One-to-One Bidirectionnel ===");
        if (entityChoice == 1) { // Depuis Student
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.one_to_one_bi.Student student = new com.example.projetjparelations.one_to_one_bi.Student("Bob");
                    com.example.projetjparelations.one_to_one_bi.Course course = new com.example.projetjparelations.one_to_one_bi.Course("Physics");
                    student.setCourse(course);
                    course.setStudent(student);
                    em.persist(student);
                    System.out.println("Créé : " + student);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Student à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_one_bi.Student found = em.find(com.example.projetjparelations.one_to_one_bi.Student.class, id);
                    System.out.println("Lu : " + (found != null ? found : "Non trouvé"));
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Student à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_one_bi.Student toUpdate = em.find(com.example.projetjparelations.one_to_one_bi.Student.class, idToUpdate);
                    if (toUpdate != null) {
                        toUpdate.getCourse().setTitle("Quantum Physics");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
                case 4: // Delete
                    System.out.print("Entrez l'ID du Student à supprimer : ");
                    Long idToDelete = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_one_bi.Student toDelete = em.find(com.example.projetjparelations.one_to_one_bi.Student.class, idToDelete);
                    if (toDelete != null) {
                        em.remove(toDelete);
                        System.out.println("Supprimé : " + toDelete);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
            }
        } else { // Depuis Course
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.one_to_one_bi.Course courseBi = new com.example.projetjparelations.one_to_one_bi.Course("Physics");
                    com.example.projetjparelations.one_to_one_bi.Student studentBi = new com.example.projetjparelations.one_to_one_bi.Student("Bob");
                    courseBi.setStudent(studentBi);
                    studentBi.setCourse(courseBi);
                    em.persist(courseBi);
                    System.out.println("Créé : " + courseBi);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Course à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_one_bi.Course found = em.find(com.example.projetjparelations.one_to_one_bi.Course.class, id);
                    System.out.println("Lu : " + (found != null ? found + ", student=" + found.getStudent() : "Non trouvé"));
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Course à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_one_bi.Course toUpdate = em.find(com.example.projetjparelations.one_to_one_bi.Course.class, idToUpdate);
                    if (toUpdate != null) {
                        toUpdate.setTitle("Advanced Physics");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Course non trouvé.");
                    }
                    break;
                case 4: // Delete
                    System.out.print("Entrez l'ID du Course à supprimer : ");
                    Long idToDelete = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_one_bi.Course toDelete = em.find(com.example.projetjparelations.one_to_one_bi.Course.class, idToDelete);
                    if (toDelete != null) {
                        em.remove(toDelete);
                        System.out.println("Supprimé : " + toDelete);
                    } else {
                        System.out.println("Course non trouvé.");
                    }
                    break;
            }
        }
    }

    private static void handleOneToManyUni(EntityManager em, int crudChoice, int entityChoice) {
        System.out.println("\n=== One-to-Many Unidirectionnel ===");
        if (entityChoice == 1) { // Depuis Student
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.one_to_many_uni.Student student = new com.example.projetjparelations.one_to_many_uni.Student("Charlie");
                    student.addCourse(new com.example.projetjparelations.one_to_many_uni.Course("Biology"));
                    student.addCourse(new com.example.projetjparelations.one_to_many_uni.Course("Chemistry"));
                    em.persist(student);
                    System.out.println("Créé : " + student);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Student à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_many_uni.Student found = em.find(com.example.projetjparelations.one_to_many_uni.Student.class, id);
                    System.out.println("Lu : " + (found != null ? found : "Non trouvé"));
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Student à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_many_uni.Student toUpdate = em.find(com.example.projetjparelations.one_to_many_uni.Student.class, idToUpdate);
                    if (toUpdate != null) {
                        toUpdate.getEnrollments().get(0).getCourse().setTitle("Advanced Biology");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
                case 4: // Delete
                    System.out.print("Entrez l'ID du Student à supprimer : ");
                    Long idToDelete = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_many_uni.Student toDelete = em.find(com.example.projetjparelations.one_to_many_uni.Student.class, idToDelete);
                    if (toDelete != null) {
                        em.remove(toDelete);
                        System.out.println("Supprimé : " + toDelete);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
            }
        } else { // Depuis Course
            System.out.println("Action impossible : Cette relation est unidirectionnelle. Course n’a pas de référence à Student.");
        }
    }

    private static void handleOneToManyBi(EntityManager em, int crudChoice, int entityChoice) {
        System.out.println("\n=== One-to-Many Bidirectionnel ===");
        if (entityChoice == 1) { // Depuis Student
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.one_to_many_bi.Student student = new com.example.projetjparelations.one_to_many_bi.Student("David");
                    student.addCourse(new com.example.projetjparelations.one_to_many_bi.Course("History"));
                    student.addCourse(new com.example.projetjparelations.one_to_many_bi.Course("Geography"));
                    em.persist(student);
                    System.out.println("Créé : " + student);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Student à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_many_bi.Student found = em.find(com.example.projetjparelations.one_to_many_bi.Student.class, id);
                    System.out.println("Lu : " + (found != null ? found : "Non trouvé"));
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Student à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_many_bi.Student toUpdate = em.find(com.example.projetjparelations.one_to_many_bi.Student.class, idToUpdate);
                    if (toUpdate != null) {
                        toUpdate.getCourses().get(0).setTitle("Modern History");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
                case 4: // Delete
                    System.out.print("Entrez l'ID du Student à supprimer : ");
                    Long idToDelete = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_many_bi.Student toDelete = em.find(com.example.projetjparelations.one_to_many_bi.Student.class, idToDelete);
                    if (toDelete != null) {
                        em.remove(toDelete);
                        System.out.println("Supprimé : " + toDelete);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
            }
        } else { // Depuis Course
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.one_to_many_bi.Course courseBi = new com.example.projetjparelations.one_to_many_bi.Course("History");
                    com.example.projetjparelations.one_to_many_bi.Student studentBi = new com.example.projetjparelations.one_to_many_bi.Student("David");
                    courseBi.setStudent(studentBi);
                    studentBi.addCourse(courseBi);
                    em.persist(studentBi);
                    System.out.println("Créé : " + courseBi);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Course à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_many_bi.Course found = em.find(com.example.projetjparelations.one_to_many_bi.Course.class, id);
                    System.out.println("Lu : " + (found != null ? found : "Non trouvé"));
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Course à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_many_bi.Course toUpdate = em.find(com.example.projetjparelations.one_to_many_bi.Course.class, idToUpdate);
                    if (toUpdate != null) {
                        toUpdate.setTitle("Modern History");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Course non trouvé.");
                    }
                    break;
                case 4: // Delete
                    System.out.print("Entrez l'ID du Course à supprimer : ");
                    Long idToDelete = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.one_to_many_bi.Course toDelete = em.find(com.example.projetjparelations.one_to_many_bi.Course.class, idToDelete);
                    if (toDelete != null) {
                        com.example.projetjparelations.one_to_many_bi.Student studentRef = toDelete.getStudent();
                        if (studentRef != null) {
                            studentRef.getCourses().remove(toDelete);
                            em.merge(studentRef);
                        }
                        em.remove(toDelete);
                        System.out.println("Supprimé : " + toDelete);
                    } else {
                        System.out.println("Course non trouvé.");
                    }
                    break;
            }
        }
    }

    private static void handleManyToOneUni(EntityManager em, int crudChoice, int entityChoice) {
        System.out.println("\n=== Many-to-One Unidirectionnel ===");
        if (entityChoice == 1) { // Depuis Student
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.many_to_one_uni.Course course = new com.example.projetjparelations.many_to_one_uni.Course("Math");
                    com.example.projetjparelations.many_to_one_uni.Student studentA = new com.example.projetjparelations.many_to_one_uni.Student("Alice");
                    com.example.projetjparelations.many_to_one_uni.Student studentB = new com.example.projetjparelations.many_to_one_uni.Student("Bob");
                    studentA.setCourse(course);
                    studentB.setCourse(course);
                    em.persist(course);
                    em.persist(studentA);
                    em.persist(studentB);
                    System.out.println("Créé : " + studentA + " et " + studentB);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Student à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_one_uni.Student found = em.find(com.example.projetjparelations.many_to_one_uni.Student.class, id);
                    System.out.println("Lu : " + (found != null ? found : "Non trouvé"));
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Student à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_one_uni.Student toUpdate = em.find(com.example.projetjparelations.many_to_one_uni.Student.class, idToUpdate);
                    if (toUpdate != null) {
                        toUpdate.getCourse().setTitle("Advanced Math");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
                case 4: // Delete
                    System.out.print("Entrez l'ID du Student à supprimer : ");
                    Long idToDelete = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_one_uni.Student toDelete = em.find(com.example.projetjparelations.many_to_one_uni.Student.class, idToDelete);
                    if (toDelete != null) {
                        em.remove(toDelete);
                        System.out.println("Supprimé : " + toDelete);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
            }
        } else { // Depuis Course
            System.out.println("Action impossible : Cette relation est unidirectionnelle. Course n’a pas de référence à Student.");
        }
    }

    private static void handleManyToOneBi(EntityManager em, int crudChoice, int entityChoice) {
        System.out.println("\n=== Many-to-One Bidirectionnel ===");
        if (entityChoice == 1) { // Depuis Student
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.many_to_one_bi.Course course = new com.example.projetjparelations.many_to_one_bi.Course("Physics");
                    com.example.projetjparelations.many_to_one_bi.Student studentA = new com.example.projetjparelations.many_to_one_bi.Student("Charlie");
                    com.example.projetjparelations.many_to_one_bi.Student studentB = new com.example.projetjparelations.many_to_one_bi.Student("David");
                    course.addStudent(studentA);
                    course.addStudent(studentB);
                    em.persist(course);
                    System.out.println("Créé : " + course);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Student à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_one_bi.Student found = em.find(com.example.projetjparelations.many_to_one_bi.Student.class, id);
                    System.out.println("Lu : " + (found != null ? found : "Non trouvé"));
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Student à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_one_bi.Student toUpdate = em.find(com.example.projetjparelations.many_to_one_bi.Student.class, idToUpdate);
                    if (toUpdate != null) {
                        toUpdate.getCourse().setTitle("Quantum Physics");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
                case 4: // Delete
                    System.out.print("Entrez l'ID du Student à supprimer : ");
                    Long idToDelete = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_one_bi.Student toDelete = em.find(com.example.projetjparelations.many_to_one_bi.Student.class, idToDelete);
                    if (toDelete != null) {
                        em.remove(toDelete);
                        System.out.println("Supprimé : " + toDelete);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
            }
        } else { // Depuis Course
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.many_to_one_bi.Course courseBi = new com.example.projetjparelations.many_to_one_bi.Course("Physics");
                    com.example.projetjparelations.many_to_one_bi.Student studentBiA = new com.example.projetjparelations.many_to_one_bi.Student("Charlie");
                    com.example.projetjparelations.many_to_one_bi.Student studentBiB = new com.example.projetjparelations.many_to_one_bi.Student("David");
                    courseBi.addStudent(studentBiA);
                    courseBi.addStudent(studentBiB);
                    em.persist(courseBi);
                    System.out.println("Créé : " + courseBi);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Course à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_one_bi.Course found = em.find(com.example.projetjparelations.many_to_one_bi.Course.class, id);
                    if (found != null) {
                        found.getStudents().size(); // Forcer le chargement
                        System.out.println("Lu : " + found);
                    } else {
                        System.out.println("Course non trouvé.");
                    }
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Course à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_one_bi.Course toUpdate = em.find(com.example.projetjparelations.many_to_one_bi.Course.class, idToUpdate);
                    if (toUpdate != null) {
                        toUpdate.setTitle("Advanced Physics");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Course non trouvé.");
                    }
                    break;
                case 4: // Delete (supprime un étudiant du cours)
                    System.out.print("Entrez l'ID du Course : ");
                    Long courseId = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_one_bi.Course courseRef = em.find(com.example.projetjparelations.many_to_one_bi.Course.class, courseId);
                    if (courseRef != null && !courseRef.getStudents().isEmpty()) {
                        com.example.projetjparelations.many_to_one_bi.Student toRemove = courseRef.getStudents().get(0);
                        courseRef.getStudents().remove(toRemove);
                        toRemove.setCourse(null);
                        em.merge(courseRef);
                        em.remove(toRemove);
                        System.out.println("Étudiant supprimé du cours : " + toRemove);
                    } else {
                        System.out.println("Course non trouvé ou aucun étudiant associé.");
                    }
                    break;
            }
        }
    }


    private static void handleManyToManyUni(EntityManager em, int crudChoice, int entityChoice) {
        System.out.println("\n=== Many-to-Many Unidirectionnel (avec table explicite) ===");
        if (entityChoice == 1) { // Depuis Student
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.many_to_many_uni.Student studentA = new com.example.projetjparelations.many_to_many_uni.Student("Grace");
                    com.example.projetjparelations.many_to_many_uni.Student studentB = new com.example.projetjparelations.many_to_many_uni.Student("Henry");
                    com.example.projetjparelations.many_to_many_uni.Course courseA = new com.example.projetjparelations.many_to_many_uni.Course("Biology");
                    com.example.projetjparelations.many_to_many_uni.Course courseB = new com.example.projetjparelations.many_to_many_uni.Course("Chemistry");
                    em.persist(courseA);
                    em.persist(courseB);
                    studentA.addEnrollment(new com.example.projetjparelations.many_to_many_uni.StudentCourse(studentA, courseA));
                    studentA.addEnrollment(new com.example.projetjparelations.many_to_many_uni.StudentCourse(studentA, courseB));
                    studentB.addEnrollment(new com.example.projetjparelations.many_to_many_uni.StudentCourse(studentB, courseA));
                    em.persist(studentA);
                    em.persist(studentB);
                    em.flush();
                    System.out.println("Créé : " + studentA);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Student à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_many_uni.Student found = em.find(com.example.projetjparelations.many_to_many_uni.Student.class, id);
                    if (found != null) {
                        found.getEnrollments().size();
                        System.out.println("Lu : " + found);
                    } else {
                        System.out.println("Non trouvé.");
                    }
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Student à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_many_uni.Student toUpdate = em.find(com.example.projetjparelations.many_to_many_uni.Student.class, idToUpdate);
                    if (toUpdate != null && !toUpdate.getEnrollments().isEmpty()) {
                        toUpdate.getEnrollments().get(0).getCourse().setTitle("Advanced Biology");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Student non trouvé ou aucun cours associé.");
                    }
                    break;
                case 4: // Delete
                    System.out.print("Entrez l'ID du Student à supprimer : ");
                    Long idToDelete = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_many_uni.Student toDelete = em.find(com.example.projetjparelations.many_to_many_uni.Student.class, idToDelete);
                    if (toDelete != null) {
                        em.remove(toDelete);
                        System.out.println("Supprimé : " + toDelete);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
            }
        } else { // Depuis Course
            System.out.println("Action impossible : Cette relation est unidirectionnelle. Course n’a pas de référence à Student.");
        }
    }

    private static void handleManyToManyBi(EntityManager em, int crudChoice, int entityChoice) {
        System.out.println("\n=== Many-to-Many Bidirectionnel (avec table explicite) ===");
        if (entityChoice == 1) { // Depuis Student
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.many_to_many_bi.Student studentA = new com.example.projetjparelations.many_to_many_bi.Student("Ivy");
                    com.example.projetjparelations.many_to_many_bi.Student studentB = new com.example.projetjparelations.many_to_many_bi.Student("Jack");
                    com.example.projetjparelations.many_to_many_bi.Course courseA = new com.example.projetjparelations.many_to_many_bi.Course("Physics");
                    com.example.projetjparelations.many_to_many_bi.Course courseB = new com.example.projetjparelations.many_to_many_bi.Course("Math");
                    com.example.projetjparelations.many_to_many_bi.StudentCourse sc1 = new com.example.projetjparelations.many_to_many_bi.StudentCourse(studentA, courseA);
                    com.example.projetjparelations.many_to_many_bi.StudentCourse sc2 = new com.example.projetjparelations.many_to_many_bi.StudentCourse(studentA, courseB);
                    com.example.projetjparelations.many_to_many_bi.StudentCourse sc3 = new com.example.projetjparelations.many_to_many_bi.StudentCourse(studentB, courseA);
                    studentA.addEnrollment(sc1);
                    studentA.addEnrollment(sc2);
                    studentB.addEnrollment(sc3);
                    courseA.addEnrollment(sc1);
                    courseA.addEnrollment(sc3);
                    courseB.addEnrollment(sc2);
                    em.persist(courseA);
                    em.persist(courseB);
                    em.persist(studentA);
                    em.persist(studentB);
                    em.flush();
                    System.out.println("Créé : " + studentA);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Student à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_many_bi.Student found = em.find(com.example.projetjparelations.many_to_many_bi.Student.class, id);
                    if (found != null) {
                        found.getEnrollments().size();
                        System.out.println("Lu : " + found);
                    } else {
                        System.out.println("Non trouvé.");
                    }
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Student à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_many_bi.Student toUpdate = em.find(com.example.projetjparelations.many_to_many_bi.Student.class, idToUpdate);
                    if (toUpdate != null && !toUpdate.getEnrollments().isEmpty()) {
                        toUpdate.getEnrollments().get(0).getCourse().setTitle("Advanced Physics");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Student non trouvé ou aucun cours associé.");
                    }
                    break;
                case 4: // Delete
                    System.out.print("Entrez l'ID du Student à supprimer : ");
                    Long idToDelete = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_many_bi.Student toDelete = em.find(com.example.projetjparelations.many_to_many_bi.Student.class, idToDelete);
                    if (toDelete != null) {
                        em.remove(toDelete);
                        System.out.println("Supprimé : " + toDelete);
                    } else {
                        System.out.println("Student non trouvé.");
                    }
                    break;
            }
        } else { // Depuis Course
            switch (crudChoice) {
                case 1: // Create
                    com.example.projetjparelations.many_to_many_bi.Course courseBiA = new com.example.projetjparelations.many_to_many_bi.Course("Physics");
                    com.example.projetjparelations.many_to_many_bi.Student studentBiA = new com.example.projetjparelations.many_to_many_bi.Student("Ivy");
                    com.example.projetjparelations.many_to_many_bi.Student studentBiB = new com.example.projetjparelations.many_to_many_bi.Student("Jack");
                    com.example.projetjparelations.many_to_many_bi.StudentCourse sc1 = new com.example.projetjparelations.many_to_many_bi.StudentCourse(studentBiA, courseBiA);
                    com.example.projetjparelations.many_to_many_bi.StudentCourse sc2 = new com.example.projetjparelations.many_to_many_bi.StudentCourse(studentBiB, courseBiA);
                    courseBiA.addEnrollment(sc1);
                    courseBiA.addEnrollment(sc2);
                    studentBiA.addEnrollment(sc1);
                    studentBiB.addEnrollment(sc2);
                    em.persist(courseBiA);
                    em.persist(studentBiA);
                    em.persist(studentBiB);
                    em.flush();
                    System.out.println("Créé : " + courseBiA);
                    break;
                case 2: // Read
                    System.out.print("Entrez l'ID du Course à lire : ");
                    Long id = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_many_bi.Course found = em.find(com.example.projetjparelations.many_to_many_bi.Course.class, id);
                    if (found != null) {
                        found.getEnrollments().size();
                        System.out.println("Lu : " + found);
                    } else {
                        System.out.println("Course non trouvé.");
                    }
                    break;
                case 3: // Update
                    System.out.print("Entrez l'ID du Course à modifier : ");
                    Long idToUpdate = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_many_bi.Course toUpdate = em.find(com.example.projetjparelations.many_to_many_bi.Course.class, idToUpdate);
                    if (toUpdate != null) {
                        toUpdate.setTitle("Advanced Physics");
                        em.merge(toUpdate);
                        System.out.println("Mis à jour : " + toUpdate);
                    } else {
                        System.out.println("Course non trouvé.");
                    }
                    break;
                case 4: // Delete (retire un étudiant du cours)
                    System.out.print("Entrez l'ID du Course : ");
                    Long courseId = Long.parseLong(scanner.nextLine());
                    com.example.projetjparelations.many_to_many_bi.Course courseRef = em.find(com.example.projetjparelations.many_to_many_bi.Course.class, courseId);
                    if (courseRef != null && !courseRef.getEnrollments().isEmpty()) {
                        com.example.projetjparelations.many_to_many_bi.StudentCourse enrollment = courseRef.getEnrollments().get(0);
                        com.example.projetjparelations.many_to_many_bi.Student studentRef = enrollment.getStudent();
                        courseRef.getEnrollments().remove(enrollment);
                        studentRef.getEnrollments().remove(enrollment);
                        em.remove(enrollment);
                        em.merge(courseRef);
                        em.merge(studentRef);
                        System.out.println("Étudiant retiré du cours : " + studentRef);
                    } else {
                        System.out.println("Course non trouvé ou aucun étudiant associé.");
                    }
                    break;
            }
        }
    }
}