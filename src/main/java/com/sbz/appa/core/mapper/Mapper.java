package com.sbz.appa.core.mapper;

public interface Mapper<A, B> {

    B mapToDto(A a);

    A mapFromDto(B b);
}
