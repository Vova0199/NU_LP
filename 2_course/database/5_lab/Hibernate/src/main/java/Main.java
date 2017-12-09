import com.my.GadgetEntity;
import com.my.CountryEntity;
import com.my.PersonEntity;
import org.hibernate.HibernateException;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Scanner;


public class Main {

    private static SessionFactory ourSessionFactory;
    private static ServiceRegistry serviceRegistry;

    static {
        try { // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory =  new Configuration().configure().buildSessionFactory();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
        } catch (Throwable ex) { throw new ExceptionInInitializerError(ex); }
    }
    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession(); //return opened session
    }
    //---------------------------------------------------------------------------
    public static void main(final String[] args) throws Exception {
        // get opened session
        Session session = getSession();
        try {

//            ReadAllTable(session);
//

//            ReadGadgetOfPerson(session);
//
//            ReadCityFilter(session);
//
//            insertCountry(session);
//            ReadCityTable(session);

//            insertPerson(session);


//            AddGadgetForPerson(session);
//            ReadAllTable(session);

//            ReadCityTable(session);
//            updateCity(session);


            System.out.println("Finish work!");
        } finally { session.close(); System.exit(0); }
    }

    private static void ReadAllTable(Session session){

//region Read Person
        Query query = session.createQuery("from " + "PersonEntity");
        System.out.format("\nTable Person --------------------\n");
        System.out.format("%3s %-12s %-12s %-10s %s\n", "ID", "Surname", "Name", "Country", "Email");
        for (Object obj : query.list()) {
            PersonEntity person = (PersonEntity) obj;
            System.out.format("%3d %-12s %-12s %-10s %s\n", person.getIdPerson(),
                    person.getSurname(), person.getName(), person.getCityByCity().getCountry(), person.getEmail());
        }
        //endregion

//region Read Book
        query = session.createQuery("from " + "GadgetEntity");
        System.out.format("\nTable gadget --------------------\n");
        System.out.format("%3s %-18s %-18s %s\n", "ID", "title", "display", "Amount");
        for (Object obj : query.list()) {
            GadgetEntity book = (GadgetEntity) obj;
            System.out.format("%3d %-18s %-18s %s\n", book.getIDGadget(), book.getTitle(), book.getDisplay(), book.getAmount());
        }
        //endregion

//region Read City
        query = session.createQuery("from " + "CountryEntity");
        System.out.format("\nTable Country --------------------\n");
        for (Object obj : query.list()) {
            CountryEntity city = (CountryEntity) obj;
            System.out.format("%s\n", city.getCountry());
        }
        //endregion

    }

    private static void ReadCityFilter(Session session){

        Scanner input = new Scanner(System.in);
        System.out.println("Input name city for Person: ");
        String city_in = input.next();

        CountryEntity cityEntity =  session.load( CountryEntity.class, city_in);
        if(cityEntity!=null){
            System.out.format("\n%s: %s\n", city_in, "Surname");
            for (PersonEntity obj : cityEntity.getPeopleByCity())
                System.out.format("    %s\n", obj.getSurname());
        }
        else System.out.println("invalid name of city");
    }

    private static void ReadGadgetOfPerson(Session session){
        Query query = session.createQuery("from " + "PersonEntity");
        System.out.format("\nTable Person --------------------\n");
        System.out.format("%3s %-12s %-12s \n","ID", "Surname", "Name");
        for (Object obj : query.list()) {
            PersonEntity person = (PersonEntity) obj;
            System.out.format("%3s %-12s %-12s->\n", person.getIdPerson(), person.getSurname(), person.getName());
            for (GadgetEntity booky : person.getGadget()) {
                System.out.format("\t\t%s // %s\n", booky.getTitle(),  booky.getDisplay());
            }
        }
    }

    private static void ReadCityTable(Session session){

        Query query = session.createQuery("from " + "CountryEntity");
        System.out.format("\nTable country --------------------\n");
        for (Object obj : query.list()) {
            CountryEntity city = (CountryEntity) obj;
            System.out.format("%s\n", city.getCountry());
        }
    }

    private static void insertCountry(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new name country: ");
        String newcity = input.next();

        session.beginTransaction();
        CountryEntity cityEntity=new CountryEntity(newcity);
        session.save(cityEntity);
        session.getTransaction().commit();

        System.out.println("end insert city");
    }

    private static void insertPerson(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("Input new Person Surname: ");
        String surname_new = input.next();
        System.out.println("Input new Person Name: ");
        String name_new = input.next();
        System.out.println("Input the country for Person: ");
        String city = input.next();
        System.out.println("Input new Person Email: ");
        String email = input.next();

        session.beginTransaction();
        PersonEntity personEntity=new PersonEntity(surname_new,name_new,city,email);
        session.save(personEntity);
        session.getTransaction().commit();
        System.out.println("end insert person");
    }

    private static void updateCity(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput a name city: ");
        String city = input.next();
        System.out.println("Input new name city: ");
        String newCity = input.next();

        CountryEntity cityEntity = session.load( CountryEntity.class, city);
        if(cityEntity!=null){
            session.beginTransaction();

            Query query1 = session.createNativeQuery("set foreign_key_checks = 0 ");
            Query query = session.createQuery("update CountryEntity set country=:code1  where country = :code2");
            query.setParameter("code1", newCity);
            query.setParameter("code2", city);
            query1.executeUpdate();

            int result = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("end update city: "+ result);
        }
        else System.out.println("There is no the city");
    }

    private static void AddGadgetForPerson(Session session){
        System.out.println("Give a gadget to person--------------");
        Scanner input = new Scanner(System.in);
        System.out.println("Choose Person Surname:");
        String surname_in = input.next();
        System.out.println("Choose gadget:");
        String book_in = input.next();

        Query query = session.createQuery("from " + "PersonEntity where surname = :code");
        query.setParameter("code", surname_in);

        if(!query.list().isEmpty()){
            //Give this person entity from query
            PersonEntity personEntity = (PersonEntity)query.list().get(0);
            //search the book entity  from query
            query = session.createQuery("from " + "GadgetEntity where title = :code");
            query.setParameter("code", book_in);
            if(!query.list().isEmpty()){
                //Give this book entity from query
                GadgetEntity bookEntity = (GadgetEntity)query.list().get(0);
                session.beginTransaction();
                personEntity.addBookEntity(bookEntity);
                session.save(personEntity);
                session.getTransaction().commit();
                System.out.println("end insert gadget for person");
            }
            else {System.out.println("There is no the book");}
        }
        else {System.out.println("There is no this person");}

    }

    private static void AddPairPersonBookWithProcedure(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput Surname for Person: ");
        String surname = input.next();
        System.out.println("Input NameBook for Book: ");
        String book = input.next();

        //to JPA 2.0
//        Query query = session.createSQLQuery(
//                "CALL InsertPersonBook(:Person, :Book)")
//                .setParameter("Person", surname)
//                .setParameter("Book", book);
//        System.out.println(query.list().get(0));

        //from JPA 2.1
        StoredProcedureQuery query = session
                .createStoredProcedureQuery("InsertPersonBook")
                .registerStoredProcedureParameter("SurmanePersonIn", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("BookNameIN", String.class, ParameterMode.IN)
                .setParameter("SurmanePersonIn", surname)
                .setParameter("BookNameIN", book);
        query.execute();
        String str = (String) query.getResultList().get(0);
        System.out.println(str);

        if(str.equals("OK")) {
            Query query2 = session.createQuery("from " + "PersonEntity");
            for (Object obj : query2.list()) {
                session.refresh(obj);
            }
            query2 = session.createQuery("from " + "GadgetEntity ");
            for (Object obj : query2.list()) {
                session.refresh(obj);
            }
        }
    }

}