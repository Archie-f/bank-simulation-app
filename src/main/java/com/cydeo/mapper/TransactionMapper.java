package com.cydeo.mapper;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionDTO convertToDTO(Transaction entity){
        //this method will accept Account entity and will convert it to DTO
        return modelMapper.map(entity,TransactionDTO.class);
    }

    public Transaction convertToEntity(TransactionDTO dto){
        //this method will accept dto and convert it to entity
        return modelMapper.map(dto,Transaction.class);
    }
}
