package com.yodishtr.alignment_service.exceptionhandler;

import java.time.LocalDateTime;

public class ApiExceptionResponse {

  private LocalDateTime localDateTime;
  private int status;
  private String error;
  private String message;
  private String path;

  public ApiExceptionResponse(LocalDateTime localDateTime, int status, String error, String message, String path) {
    this.localDateTime = localDateTime;
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }

  // Getters
  public LocalDateTime getLocalDateTime() {
    return this.localDateTime;
  }

  public int getStatus() {
    return this.status;
  }

  public String getError() {
    return this.error;
  }

  public String getMessage() {
    return this.message;
  }

  public String getPath() {
    return this.path;
  }

  // Setters
}
