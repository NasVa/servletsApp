package mapper.impl;

import dto.ReaderDto;
import mapper.Mapper;
import models.Reader;

public class ReaderMapper implements Mapper<Reader, ReaderDto> {
    private static class InstanceHolder{
        private static final ReaderMapper INSTANCE = new ReaderMapper();
    }
    public static ReaderMapper getInstance() { return InstanceHolder.INSTANCE;}
    @Override
    public ReaderDto toDto(Reader reader) {
        ReaderDto readerDto = new ReaderDto();
        readerDto.setId(reader.getId());
        readerDto.setName(reader.getName());
        readerDto.setMail(reader.getMail());
        readerDto.setPhone(reader.getPhone());
        return readerDto;
    }

    @Override
    public Reader toEntity(ReaderDto readerDto) {
        Reader reader = new Reader();
        reader.setId(readerDto.getId());
        reader.setName(readerDto.getName());
        reader.setMail(readerDto.getMail());
        reader.setPhone(readerDto.getPhone());
        return reader;
    }
}
