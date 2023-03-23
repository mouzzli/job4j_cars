package ru.job4j.cars.mappper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PostPhoto;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "postPhotos", target = "postPhotos", qualifiedByName = "byteToBase64Converter")
    List<PostDto> toPostDto(List<Post> post);

    @Named("byteToBase64Converter")
    static List<String> byteToBase64Converter(List<PostPhoto> postPhotos) {
        return postPhotos.stream()
                .map(postPhoto -> Base64.getEncoder().encodeToString(postPhoto.getFile()))
                .collect(Collectors.toList());
    }

    default List<String> map(List<PostPhoto> value) {
        return byteToBase64Converter(value);
    }
}
