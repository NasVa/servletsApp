package service.impl;

import dto.ReaderDto;
import exception.NotFondException;
import mapper.impl.ReaderMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.ReaderRepository;
import repository.impl.ReaderRepositoryImpl;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static service.impl.ServiceMocks.*;
public class ReaderServiceImplTest {
    @Test
    void createTest(){
        ReaderDto readerDto = createReaderDto();
        ReaderDto actual = readerService.create(readerDto);
        readerDto.setId(ServiceMocks.READER_SAVE_ID);
        Assertions.assertEquals(readerDto, actual);

//        ReaderDto serviceReaderDto = new ReaderDto();
//        serviceReaderDto.setName("n");
//        serviceReaderDto.setMail("m");
//        serviceReaderDto.setPhone(132343);
//        ReaderRepository readerRepository = mock(ReaderRepositoryImpl.class);
//        doAnswer(invocationOnMock -> {
//            Reader argument = invocationOnMock.getArgument(0, Reader.class);
//            argument.setId(12);
//            return null;
//        }).when(readerRepository).save(ReaderMapper.getInstance().toEntity(serviceReaderDto));
//        ReaderServiceImpl readerService = new ReaderServiceImpl(readerRepository);
//        ReaderDto actualDto = readerService.create(serviceReaderDto);
//        serviceReaderDto.setId(12);
//        Assertions.assertEquals(serviceReaderDto, actualDto);
    }

    @Test
    void updateTest(){
        ReaderDto expected = createReaderDto();
        expected.setId(12);
        ReaderDto actual = readerService.update(expected);
        Assertions.assertEquals(expected, actual);

//        ReaderDto serviceReaderDto = new ReaderDto();
//        serviceReaderDto.setName("n");
//        serviceReaderDto.setMail("m");
//        serviceReaderDto.setPhone(132343);
//        ReaderRepository readerRepository = mock(ReaderRepositoryImpl.class);
//        ReaderServiceImpl readerService = new ReaderServiceImpl(readerRepository);
//        Assertions.assertThrows(ValidationException.class,
//                () -> {
//                    readerService.update(serviceReaderDto);
//                }
//            ,"Reader id must be not null");
    }

    @Test
    void getByIdTest(){
        ReaderDto expected = readerMapper.toDto(readerRepository.getById(129));
        ReaderDto actual = readerService.getById(129);
        Assertions.assertEquals(expected, actual);

//        ReaderDto serviceReaderDto = new ReaderDto();
//        serviceReaderDto.setId(12);
//        serviceReaderDto.setName("n");
//        serviceReaderDto.setMail("m");
//        serviceReaderDto.setPhone(132343);
//        ReaderRepository readerRepository = mock(ReaderRepositoryImpl.class);
//        doAnswer(invocationOnMock -> {
//            return ReaderMapper.getInstance().toEntity(serviceReaderDto);
//        }).when(readerRepository).getById(12);
//        ReaderServiceImpl readerService = new ReaderServiceImpl(readerRepository);
//        ReaderDto actualDto = readerService.getById(12);
//        Assertions.assertEquals(serviceReaderDto, actualDto);
    }

    @Test
    void getByWrongIdTest(){
        ReaderDto serviceReaderDto = new ReaderDto();
        serviceReaderDto.setId(15);
        serviceReaderDto.setName("n");
        serviceReaderDto.setMail("m");
        serviceReaderDto.setPhone(132343);
        ReaderRepository readerRepository = mock(ReaderRepositoryImpl.class);
        doAnswer(invocationOnMock -> {
            return ReaderMapper.getInstance().toEntity(serviceReaderDto);
        }).when(readerRepository).getById(15);
        ReaderServiceImpl readerService = new ReaderServiceImpl(readerRepository);
        Assertions.assertThrows(NotFondException.class,
                () -> {
                    readerService.getById(12);
                }
                ,"No one reader found");
    }

    @Test
    void deleteTest(){
        Assertions.assertEquals(1, readerService.delete(11));

//        ReaderDto serviceReaderDto = new ReaderDto();
//        serviceReaderDto.setId(12);
//        serviceReaderDto.setName("n");
//        serviceReaderDto.setMail("m");
//        serviceReaderDto.setPhone(132343);
//        ReaderRepository readerRepository = mock(ReaderRepositoryImpl.class);
//        doAnswer(invocationOnMock -> {
//            return 1;
//        }).when(readerRepository).deleteById(12);
//        ReaderServiceImpl readerService = new ReaderServiceImpl(readerRepository);
//        int deleted = readerService.delete(serviceReaderDto.getId());
//        Assertions.assertEquals(1, deleted);
    }

    private static ReaderDto createReaderDto() {
        ReaderDto readerDto = new ReaderDto();
        readerDto.setName("reader");
        readerDto.setMail("mail");
        readerDto.setPhone(87395);
        return readerDto;
    }
}

