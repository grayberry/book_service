package am.dreamteam.bookservice;

import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.entities.books.Category;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.service.TransferService;
import am.dreamteam.bookservice.service.UserService;
import am.dreamteam.bookservice.service.impl.*;
import am.dreamteam.bookservice.util.HibernateUtil;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean signedIn = false;
    private static User user = null;

    public static void main(String[] args) {

        loop: while (true){
        String command;
            if(!signedIn) {
                System.out.println("R -> Registration");
                System.out.println("L -> Login");
                System.out.println("Q -> Quit");
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
                System.out.println("2 -> Add a book");
                System.out.println("3 -> Show my books");
                System.out.println("4 -> Proceed transfer");
                System.out.println("L -> Log out");
                System.out.println("Q -> Quit");
                command = scanner.nextLine();
                switch (command.toUpperCase()){

                    case "1": showAllBooks();
                        break;
                    case "2": addBook();
                        break;
                    case "3": showUserBooks();
                        break;
                    case "4": transfer();
                        break;
                    case "L": signedIn=false; user=null;
                        break;
                    case "Q": quit();
                        break loop;
                }
            }
        }
    }

    private static void reg(){
        UserService userService = new UserServiceImpl();
        String username;
        String pass;
        String email;
        String phoneNumber;
        String sex;

        while(true) {
            System.out.println("enter username");
            username = scanner.nextLine();
            System.out.println("enter pass");
            pass = scanner.nextLine();
            System.out.println("enter email");
            email = scanner.nextLine();
            System.out.println("enter phone number");
            phoneNumber = scanner.nextLine();
            System.out.println("your sex: M or F");
            sex = scanner.nextLine();

            User user;
            if ((user = userService.regUser(username, pass, email, phoneNumber, sex)) != null) {
                System.out.println("new user " + username + " created");
                break;
            }
        }

    }

    private static void signIn(){
        UserService userService = new UserServiceImpl();
        String login;
        String pass;

        while(true) {
            System.out.println("Enter login");
            login = scanner.nextLine();

            if(userService.checkUser(login) == null) {
                continue;
            }


            System.out.println("Enter password");
            pass = scanner.nextLine();
            if((user = userService.login(login, pass)) == null) {
                System.out.println(user);
                System.out.println("Password is not correct");
                continue;
            }

            signedIn = true;
            break;
        }
    }

    private static void showAllBooks(){
       List<UserBooks> usersBooks = new UsersBooksServiceImpl().getUsersBooksList();
       usersBooks.forEach(System.out::println);
    }

    private static void addBook(){
        BookServiceImpl bookService = new BookServiceImpl();
        UsersBooksServiceImpl usersBooksService = new UsersBooksServiceImpl();

        System.out.println("Author full name");
        String authorsNamesString = scanner.nextLine();
        String[] authorsNamesArray = authorsNamesString.split(",");
        Set<Author> authors = new AuthorServiceImpl().getAuthorsSet(authorsNamesArray);

        System.out.println("category");
        String categoriesNamesString = scanner.nextLine();
        String[] categoriesNamesArray = categoriesNamesString.split(", ");
        Set<Category> categories = new CategoryServiceImpl().getCategoriesSet((categoriesNamesArray));

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
        UserBooks usersBooks = new UserBooks(user, book);
        usersBooksService.addUsersBooks(usersBooks);
    }

    public static void showUserBooks(){
        UserService userService = new UserServiceImpl();
        List<UserBooks> usersBooks = userService.getUsersBooks(user);
        usersBooks.forEach(System.out::println);
    }

    public static void transfer(){
        UserService userService = new UserServiceImpl();
        TransferService transferService = new TransferServiceImpl();

        User user1 = userService.getUserById(7); //hl@ vor dzerov @Ntrelem user-in
        List<UserBooks> myBooksList = userService.getUsersBooks(user);
        if(!forEachBooks(myBooksList)){
            System.out.println("you don't have books for transfer");
            return;
        }
        System.out.println("choose your book id");
        int id1 = Integer.valueOf(scanner.nextLine());

        List<UserBooks> hisBooksList = userService.getUsersBooks(user1);
        if(!forEachBooks(hisBooksList)){
            System.out.println("user has no books");
            return;
        }

        System.out.println("choose book id for change");
        int id2 = Integer.valueOf(scanner.nextLine());

        UserBooks usersBooks1 = myBooksList.get(id1-1);
        UserBooks usersBooks2 = hisBooksList.get(id2-1);

        System.out.print("change book '" + usersBooks1.getBook().getTitle() +
                "' to book '" + usersBooks2.getBook().getTitle() + "'?\n Y:");
        if(!scanner.nextLine().toUpperCase().equals("Y")){
            return;
        }
        transferService.createTransfer(user, user1, usersBooks1, usersBooks2);
        System.out.println("DONE!");
    }

    private static boolean forEachBooks(List<UserBooks> booksList){
        if(!booksList.isEmpty()) {
            System.out.println(booksList.get(0).getUser().getUsername() + " books list");
            for (int i = 0; i < booksList.size(); i++) {
                System.out.println(i + 1 + " '" + booksList.get(i).getBook().getTitle() +
                        "' - " + booksList.get(i).getBook().getAuthors());
            }
            return true;
        }
        return false;
    }

    public static void quit(){
        System.out.println("Good Bye");
        HibernateUtil.shutDown();
    }
}
