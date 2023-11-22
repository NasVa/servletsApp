package service.impl;

import dto.ReaderDto;
import exception.NotFondException;
import exception.ValidationException;
import mapper.impl.ReaderMapper;
import models.Reader;
import repository.ReaderRepository;
import repository.impl.ReaderRepositoryImpl;
import service.ReaderService;

public class ReaderServiceImpl implements ReaderService {

    private static class InstanceHolder {
        private static final ReaderService INSTANCE = new ReaderServiceImpl(ReaderRepositoryImpl.getInstance());
    }

    public static ReaderService getInstance() {
        return ReaderServiceImpl.InstanceHolder.INSTANCE;
    }

    private final ReaderMapper readerMapper = ReaderMapper.getInstance();
    private final ReaderRepository readerRepository;

    public ReaderServiceImpl(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public ReaderDto create(ReaderDto readerDto) {
        if (readerDto.getId() != null) {
            throw new ValidationException("Reader id must be null");
        }
        Reader entity = readerMapper.toEntity(readerDto);
        readerRepository.save(entity);
        return readerMapper.toDto(entity);
    }

    @Override
    public int delete(int id) {
        return readerRepository.deleteById(id);
    }

    @Override
    public ReaderDto update(ReaderDto readerDto) {
        if (readerDto.getId() == null) {
            throw new ValidationException("Reader id must be not null");
        }
        Reader entity = readerMapper.toEntity(readerDto);
        readerRepository.save(entity);
        return readerMapper.toDto(entity);
    }

    @Override
    public ReaderDto getById(int id) {
        Reader reader = readerRepository.getById(id);
        if (reader == null) {
            throw new NotFondException("No one reader found");
        }
        return readerMapper.toDto(reader);
    }
}
