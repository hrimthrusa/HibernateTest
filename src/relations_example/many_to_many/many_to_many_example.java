package relations_example.many_to_many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import relations_example.many_to_many.entity.Child;
import relations_example.many_to_many.entity.Section;

public class many_to_many_example {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Child.class)
                .addAnnotatedClass(Section.class)
                .buildSessionFactory();

        Session session = null;

        try {
            session = factory.getCurrentSession();

            // Добавление в БД c CascadeType.ALL
            Section section1 = new Section("Soccer");

            Child child1 = new Child("John", 7);
            Child child2 = new Child("Jack", 8);
            Child child3 = new Child("Mark", 6);
            section1.addChildToSection(child1);
            section1.addChildToSection(child2);
            section1.addChildToSection(child3);

            session.beginTransaction();
            session.save(section1);
            session.getTransaction().commit();


            // Добавление в БД cо всеми CascadeType кроме "ALL"
            Section section11 = new Section("Soccer");

            Child child11 = new Child("John", 7);
            Child child22 = new Child("Jack", 8);
            Child child33 = new Child("Mark", 6);

            session.beginTransaction();
            session.save(section1);

            section1.addChildToSection(child1);
            section1.addChildToSection(child2);
            section1.addChildToSection(child3);

            session.save(child11);
            session.save(child22);
            session.save(child33);

            session.getTransaction().commit();


            // Получение информации из БД
            session.beginTransaction();

            Section section = session.get(Section.class, 1);
            System.out.println(section);
            System.out.println(section.getChildren());

            Child child = session.get(Child.class, 1);
            System.out.println(child);
            System.out.println(child.getSections());

            session.getTransaction().commit();


            // Удаление секции
            session.beginTransaction();
            Section section2 = session.get(Section.class, 1);
            session.delete(section2);

            session.getTransaction().commit();

        } finally {
            session.close();
            factory.close();
        }
    }
}
