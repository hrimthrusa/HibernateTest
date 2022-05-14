package relations_example.one_to_one_uni;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import relations_example.one_to_one_uni.entity.Detail;
import relations_example.one_to_one_uni.entity.Employee;

public class one_to_one_uni_example {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Detail.class)
                .buildSessionFactory();

        Session session = null;

        try {
            session = factory.getCurrentSession();
            Employee employee = new Employee("John", "Deer", "IT", 500);
            Detail detail = new Detail("London", "123456789", "john@mail.com");

            employee.setEmpDetail(detail);
            session.beginTransaction();

            session.save(employee);  // Благодаря cascade детали сохранятся в соответствующей таблице

            // Получение деталей через объект Employee
            Employee emp = session.get(Employee.class, 1);
            System.out.println(emp.getEmpDetail());

            // Удаление двух записей каскадом
            session.delete(emp);
            
            session.getTransaction().commit();

        } finally {
            session.close();
            factory.close();
        }
    }
}
