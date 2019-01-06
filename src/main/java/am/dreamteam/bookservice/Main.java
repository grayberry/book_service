package am.dreamteam.bookservice;

import am.dreamteam.bookservice.dao.impl.*;
import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.entities.books.Category;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;
import am.dreamteam.bookservice.service.impl.*;
import am.dreamteam.bookservice.util.HibernateUtil;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean signIn = false;
    private static User user = null;

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
                System.out.println("4 -> transfer");
                System.out.println("L -> LogOut");
                System.out.println("Q -> quit");
                command = scanner.nextLine();
                switch (command.toUpperCase()){

                    case "1": one();
                        break;
                    case "2": two();
                        break;
                    case "3": three();
                        break;
                    case "4": four();
                        break;
                    case "L": signIn=false; user=null;
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

        user = new User(login, pass,email,phoneNumber,sex);
        if(new UserDAOImpl().regUser(user)) {
            System.out.println("new user " + login + " created");
        } else {
            System.out.println("Something goes wrong");
        }

    }

    private static void signIn(){
        String login;
        System.out.println("your login");
        login = scanner.nextLine();


        if(!(new UserDAOImpl().checkUser(login))){
            System.out.println("Your login is not correct, please try again");
            signIn();
        }
        else {

            for(int i = 5; i>0; i--){
                System.out.println("enter pass");
                if((user = new UserDAOImpl().login(login, scanner.nextLine()))!=null){
                    System.out.println("Welcome " + user.getLogin());
                    signIn = true;
                    break;
                }else {
                    System.out.println("password is not correct. " + i + " more left");
                }
            }
        }
    }

    private static void one(){
       List<UsersAddBooks> usersAddBooks = new UsersAddBooksDAOImpl().getListUsersAddBooksList();
       usersAddBooks.forEach(System.out::println);
    }

    private static void two(){

        BookServiceImpl bookService = new BookServiceImpl();
        UsersAddBooksServiceImpl usersAddBooksService = new UsersAddBooksServiceImpl();

        System.out.println("Author full name");
        Set<Author> authors = new AuthorServiceImpl().getAuthorsList(scanner.nextLine());
        System.out.println("category");
        Set<Category> categories = new CategoryServiceImpl().getCategoriesSet(scanner.nextLine());

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

        Book book = null;
        if((book = bookService.checkBookUnique(title, language, authors))==null){
            book = new Book(title, language, pageCount, imageRef, description, isbn10, isbn13);
            bookService.addBook(book, authors, categories);
        }
        UsersAddBooks usersAddBooks = new UsersAddBooks(user, book);
        usersAddBooksService.addUsersAddBooks(usersAddBooks);
    }

    public static void three(){
        UserServiceImpl userService = new UserServiceImpl();
        List<UsersAddBooks> usersAddBooks = userService.getUsersAddBooks(user);
        usersAddBooks.forEach(System.out::println);
    }

    public static void four(){
        UserServiceImpl userService = new UserServiceImpl();
        UsersAddBooksServiceImpl usersAddBooksService = new UsersAddBooksServiceImpl();
        TransferServiceImpl transferService = new TransferServiceImpl();
        User user1 = userService.findUserById(2); //hl@ vor dzerov @Ntrelem 2id ov user-in
        System.out.println("My books");
        user.getUserBooks().forEach(System.out::println);
        System.out.println("his books");
        user1.getUserBooks().forEach(System.out::println);

        System.out.println("choose your book id");
        int id1 = scanner.nextInt();
        System.out.println("choose book id for change");
        int id2 = scanner.nextInt();

        UsersAddBooks usersAddBooks1 = usersAddBooksService.findUsersAddBooksById(id1);
        UsersAddBooks usersAddBooks2 = usersAddBooksService.findUsersAddBooksById(id2);
        transferService.createTransfer(user, user1, usersAddBooks1, usersAddBooks2);

    }

    public static void quit(){
        System.out.println("Good Bye");
        HibernateUtil.shutDown();
    }
}
