package com.example.mapper;

import com.example.model.Quote;
import com.example.dto.QuoteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // MapStruct: делает маппер компонентом Spring
public interface QuoteMapper {

    QuoteMapper INSTANCE = Mappers.getMapper(QuoteMapper.class);

    QuoteDTO toDto(Quote quote); // Преобразует Entity в DTO
    Quote toEntity(QuoteDTO quoteDTO); // Преобразует DTO в Entity
}