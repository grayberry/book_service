package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.entities.messages.Dialog;
import am.dreamteam.bookservice.entities.users.Transfer;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.repositories.DialogsRepository;
import am.dreamteam.bookservice.repositories.TransfersRepository;
import am.dreamteam.bookservice.repositories.UsersBooksRepository;
import am.dreamteam.bookservice.repositories.UsersRepository;
import am.dreamteam.bookservice.service.TransferService;
import am.dreamteam.bookservice.util.MailSendHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    private TransfersRepository transfersRepository;
    private UsersRepository usersRepository;
    private UsersBooksRepository usersBooksRepository;
    private DialogsRepository dialogsRepository;
    private MailSendHelper mailSendHelper;

    public TransferServiceImpl(TransfersRepository transfersRepository,
                               UsersRepository usersRepository,
                               UsersBooksRepository usersBooksRepository,
                               DialogsRepository dialogsRepository,
                               MailSendHelper mailSendHelper) {
        this.transfersRepository = transfersRepository;
        this.usersRepository = usersRepository;
        this.usersBooksRepository = usersBooksRepository;
        this.dialogsRepository = dialogsRepository;
        this.mailSendHelper = mailSendHelper;
    }

    @Override
    public void createOneTransfer(String userFrom, String userTo, Integer bookId) {
        User userF = usersRepository.findUserByUsername(userFrom);
        User userT = usersRepository.findUserByUsername(userTo);
        UserBooks book = usersBooksRepository.getOne(bookId);

        Transfer transfer = new Transfer(userF, userT, book);
        transfersRepository.save(transfer);
    }

    @Override
    public List<Transfer> findAllByUserFromAndUserToAndDone(String userFrom, String userTo, Boolean done) {

        User userF = usersRepository.findUserByUsername(userFrom);
        User userT = usersRepository.findUserByUsername(userTo);
        return transfersRepository.findAllByUserFromAndUserToAndDone(userF, userT, false);
    }
    public boolean findTransfer(String userFrom, String userTo, Integer bookId){
        UserBooks book = usersBooksRepository.getOne(bookId);
        User userF = usersRepository.findUserByUsername(userFrom);
        User userT = usersRepository.findUserByUsername(userTo);

        if(dialogsRepository.findByUserFromAndUserTo(userF, userT)==null){
            newDialog(userF, userT);
        }
        return transfersRepository.findByUserFromAndUserToAndUserBooksAndDone(userF, userT, book, false)!=null;
    }

    @Override
    public void cancelTransfer(String userFrom, String userTo, Integer bookId) {
        User userF = usersRepository.findUserByUsername(userFrom);
        User userT = usersRepository.findUserByUsername(userTo);
        UserBooks book = usersBooksRepository.getOne(bookId);

        transfersRepository
                .delete(transfersRepository
                        .findByUserFromAndUserToAndUserBooksAndDone(userF, userT, book, false));
    }

    @Override
    public void transferBooks(String userFrom, String userTo, Integer bookFrom, Integer bookTo) {
        User userF = usersRepository.findUserByUsername(userFrom);
        User userT = usersRepository.findUserByUsername(userTo);

        UserBooks bookF = usersBooksRepository.getOne(bookFrom);
        UserBooks bookT = usersBooksRepository.getOne(bookTo);

        Transfer transfer = transfersRepository.findByUserFromAndUserToAndUserBooksAndDone(userT, userF, bookF, false);
        transfer.setDone(true);
        transfersRepository.save(transfer);

        List<Transfer> transfers = transfersRepository.findAllByUserToAndUserBooksAndDone(userF, bookF, false);
        transfersRepository.deleteAll(transfers);

        Transfer newTransfer = new Transfer(userF, userT, bookT);
        newTransfer.setDone(true);
        transfersRepository.save(newTransfer);

        bookF.setUser(userT);
        bookT.setUser(userF);
        usersRepository.save(userF);
        usersRepository.save(userT);

        if(dialogsRepository.findByUserFromAndUserTo(userF, userT)==null){
            newDialog(userF, userT);
        }
        mailSendHelper.sendTransferNotification(userT.getEmail(), userFrom, bookF.getBook().getTitle());
    }

    @Override
    public List<Transfer> findAllByUserToAndDone(String userTo, Boolean done) {
        User user = usersRepository.findUserByUsername(userTo);

        return transfersRepository.findAllByUserToAndDone(user, done);

    }

    private void newDialog(User userFrom, User userTo){
        Dialog dialog = new Dialog();
        dialog.setUserFrom(userFrom);
        dialog.setUserTo(userTo);
        dialogsRepository.save(dialog);
    }
}
