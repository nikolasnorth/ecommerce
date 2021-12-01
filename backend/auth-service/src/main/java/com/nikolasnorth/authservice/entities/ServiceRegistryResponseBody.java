package com.nikolasnorth.authservice.entities;

public class ServiceRegistryResponseBody {

  private int id;

  private String serviceName;

  private String serviceLocation;

  private String serviceStatus;

  protected ServiceRegistryResponseBody(){
  }

  public ServiceRegistryResponseBody(int id, String serviceName, String serviceLocation, String serviceStatus) {
    this.id = id;
    this.serviceName = serviceName;
    this.serviceLocation = serviceLocation;
    this.serviceStatus = serviceStatus;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceLocation() {
    return serviceLocation;
  }

  public void setServiceLocation(String serviceLocation) {
    this.serviceLocation = serviceLocation;
  }

  public String getServiceStatus() {
    return serviceStatus;
  }

  public void setServiceStatus(String serviceStatus) {
    this.serviceStatus = serviceStatus;
  }
}
