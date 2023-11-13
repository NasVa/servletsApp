package service.impl;

import db.ConnectionManager;
import db.impl.ConnectionManagerImpl;
import models.Reader;
import repository.ReaderRepository;
import repository.impl.ReaderRepositoryImpl;
import service.ReaderService;

public class ReaderServiceImpl implements ReaderService {
    private ReaderRepository readerRepository = new ReaderRepositoryImpl(ConnectionManagerImpl.getInstance());
    @Override
    public void createReader(String name, String mail, int phone) {
        //Reader reader = new Reader(name, mail, phone);
        //readerRepository.save(reader);
    }
}
