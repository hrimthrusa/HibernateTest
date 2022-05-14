package hibernate_example;

import hibernate_example.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CRUD_example {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        try {
            // Новая запись в БД
            Session session = factory.getCurrentSession();
            Employee emp = new Employee("John", "Deer", "IT", 500);

            session.beginTransaction();         // open
            session.save(emp);
            session.getTransaction().commit();  // close


            // Получение записи из БД по id в виде объекта
            int myId = emp.getId();
            Session session1 = factory.getCurrentSession();

            session1.beginTransaction();
            Employee employee = session1.get(Employee.class, myId);
            session.getTransaction().commit();


            // Получения списка всех работников с использованием HQL
            Session session2 = factory.getCurrentSession();
            session2.beginTransaction();
            List<Employee> emps = session.createQuery("FROM Employee").getResultList();

            for (Employee e : emps)
                System.out.println(e);

            session2.getTransaction().commit();


            // Изменение записи в БД с помощью сеттера и HQL для одной записи
            Session session3 = factory.getCurrentSession();

            session.beginTransaction();
            Employee employee3 = session.get(Employee.class, 1);
            emp.setSalary(1500);

            session.createQuery("UPDATE Employee SET salary = 1000 " +
                    "WHERE name = 'John'").executeUpdate();

            session3.getTransaction().commit();


            // Удаление записи с помощью геттера и HQL для одной записи
            Session session4 = factory.getCurrentSession();

            session4.beginTransaction();
            Employee emp1 = session.get(Employee.class, 1);
            session4.delete(emp1);

            session4.createQuery("DELETE Employee " +
                    "WHERE name = 'John'").executeUpdate();

            session4.getTransaction().commit();

        } finally {
            factory.close();
        }
    }

}
