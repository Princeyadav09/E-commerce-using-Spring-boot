package com.e.commerce.service.mapper;

import com.e.commerce.service.exception.MappingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonMapper {

    private final ModelMapper modelMapper;

    <T, S> S convertToEntity(T data, Class<S> type) {
        try {
            return modelMapper.map(data, type);
        } catch (MappingException ex){
            throw new MappingException("error");
        }
    }

//    <T, S> S convertToResponse(T data, Class<S> type) {
//        return modelMapper.map(data, type);
//    }
//
//    <T, S> List<S> convertToResponseList(List<T> lists, Class<S> type) {
//        return lists.stream()
//                .map(list -> convertToResponse(list, type))
//                .collect(Collectors.toList());
//    }
//
//    <T, S> HeaderResponse<S> getHeaderResponse(List<T> orders, Integer totalPages, Long totalElements, Class<S> type) {
//        List<S> orderResponses = convertToResponseList(orders, type);
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add("page-total-count", String.valueOf(totalPages));
//        responseHeaders.add("page-total-elements", String.valueOf(totalElements));
//        return new HeaderResponse<S>(orderResponses, responseHeaders);
//    }
}