package am.dreamteam.bookservice;

import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.entities.books.Category;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.*;

public class Main {
    private static Session session = HibernateUtil.getSessionFactory().openSession();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean signIn = false;
    private static User user = null;
    private static List<Book> books = null;

    public static void main(String[] args) {

        loop: while (true){
        String command;
            if(!signIn) {
                System.out.println("R -> Registration");
                System.out.println("L -> Login");
                System.out.println("q -> quit");
                command = scanner.nextLine();
                switch (command.toUpperCase()){
                    case "R": reg();
                        break;
                    case "L": signIn();
                        break;
                    case "Q" : quit();
                    break loop;

                }
            }else {
                System.out.println("1 -> Show all books");
                System.out.println("2 -> add book");
                System.out.println("3 -> user books");
                System.out.println("Q -> quit");
                command = scanner.nextLine();
                switch (command.toUpperCase()){

                    case "1": one();
                        break;
                    case "2": two();
                        break;
                    case "3": three();
                        break;
                    case "Q": quit();
                        break loop;
                }
            }
        }
    }

    private static void reg(){
        String login;
        String pass;
        String email;
        String phoneNumber;
        String sex;
        System.out.println("enter login");
        login = scanner.nextLine();
        System.out.println("enter pass");
        pass = scanner.nextLine();
        System.out.println("enter email");
        email = scanner.nextLine();
        System.out.println("enter phone number");
        phoneNumber = scanner.nextLine();
        System.out.println("your sex: M or F");
        sex = scanner.nextLine();

        session.beginTransaction();
        user = new User(login, pass,email,phoneNumber,sex);
        session.save(user);
        session.getTransaction().commit();
        System.out.println("new user " + login + " created");

    }

    private static void signIn(){
        String login;
        System.out.println("your login");
        login = scanner.nextLine();

        TypedQuery<User> query = session.createQuery("from User where login = :l", User.class);
        query.setParameter("l", login);
        List<User> userList = query.getResultList();
        if(userList.isEmpty()){
            System.out.println("Your login is not correct, please try again");
            signIn();
        }
        else {
            user = userList.get(0);
            for(int i = 5; i>0; i--){
                System.out.println("enter pass");
                if(user.getPass().equals(scanner.nextLine())){
                    System.out.println("Welcome " + user.getLogin());
                    System.out.println("your books list + " + user.getUserBooks());
                    signIn = true;
                    break;
                }else {
                    System.out.println("password is not correct. " + i + " more left");
                }
            }
        }
    }

    private static void one(){
        TypedQuery<Book> q = session.createQuery("from Book", Book.class);
        List<Book> b = q.getResultList();
        b.forEach(x -> {
            x.getUsersAddBooks().forEach(System.out::println);
        });

    }

    private static void two(){
        session.beginTransaction();
        Author author = new Author();
        System.out.println("Author full name");
        author.setFullName(scanner.nextLine());

        Category category = new Category();
        System.out.println("category");
        category.setCategory(scanner.nextLine());

        Book book = null;
        String title;
        String language;
        int pageCount = 250;
        String imageRef = "image.jpg";
        String description = "lav girq";
        String isbn10 = "123";
        String isbn13 = "546789";

        System.out.println("title");
        title = scanner.nextLine();
        System.out.println("language");
        language = scanner.nextLine();

        TypedQuery<Book> query = session.createQuery("from Book where title =:t and language = :l", Book.class);
        query.setParameter("t", title);
        query.setParameter("l", language);
        List<Book> bookList = query.getResultList();

        if(!bookList.isEmpty()){
            book = bookList.get(0);
        }else {
            book = new Book(title, language, pageCount,imageRef,description,isbn10,isbn13);
            Set<Author> authorList =  new HashSet<>();
            authorList.add(author);
            book.setAuthors(authorList);

            Set<Category> categoryList = new HashSet<>();
            categoryList.add(category);
            book.setCategories(categoryList);

            session.save(author);
            session.save(category);
            session.save(book);
        }

        user.appendBooks(book);
        session.getTransaction().commit();

    }

    public static void three(){
        user.getUserBooks().forEach(System.out::println);
    }

    public static void quit(){
        System.out.println("Good Bye");
        HibernateUtil.shutDown();
    }
}
