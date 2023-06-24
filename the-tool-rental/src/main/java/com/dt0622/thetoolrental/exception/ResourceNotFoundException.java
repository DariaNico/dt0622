package com.dt0622.thetoolrental.exception;

public class ResourceNotFoundException extends Exception {
  public ResourceNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
