package mapper;

public interface Mapper<E, D> {
    D toDto(E e);

    E toEntity(D d);
}
