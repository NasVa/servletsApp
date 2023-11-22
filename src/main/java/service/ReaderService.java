package service;

import dto.ReaderDto;

public interface ReaderService {
    ReaderDto create(ReaderDto readerDto);

    int delete(int id);

    ReaderDto update(ReaderDto readerDto);

    ReaderDto getById(int id);
}
