package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.users.Transfer;

import java.util.List;

public interface TransferService {
    void createOneTransfer(String userFrom, String userTo, Integer bookId);
    List<Transfer> findAllByUserFromAndUserToAndDone(String userFrom, String userTo, Boolean done);
    boolean findTransfer(String userFrom, String userTo, Integer bookId);
    void cancelTransfer(String userFrom, String userTo, Integer bookId);
    void transferBooks(String userFrom, String userTo, Integer bookFrom, Integer bookTo);
}
