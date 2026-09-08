package com.yodishtr.alignment_service.service;

/*
 * will need to do the following:
 * 1) receive/create the request dto and the extracted tenant context
 * 2) invoke the file storage component (through the interface) to save the
 * fasta input sequence string
 * nd obtain the inputReference
 * 3) pass the dto, the generated inputReference and tenantId to the custom
 * mapper to construct the alignment job entity
 * 4) save the entity to the database
 */

public class JobService {
}
