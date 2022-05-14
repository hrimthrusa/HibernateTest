package relations_example.one_to_one_bi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import relations_example.one_to_one_bi.entity.Detail;
import relations_example.one_to_one_bi.entity.Employee;

public class one_to_one_bi_example {
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

            // Установка двунаправленной связи
            employee.setEmpDetail(detail);
            detail.setEmployee(employee);

            session.beginTransaction();

            session.save(employee);

            // Получение работника через объект Detail
            Detail detail1 = session.get(Detail.class, 2);
            System.out.println(detail1.getEmployee());

            // Удаление двух записей каскадом
            session.delete(detail1);
            
            session.getTransaction().commit();

        } finally {
            session.close();
            factory.close();
        }
    }
}
