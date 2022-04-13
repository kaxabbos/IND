package com.ind.repo;

import com.ind.models.Devices;
import com.ind.models.enums.DeviceType;
import com.ind.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoDevices extends JpaRepository<Devices, Long> {
    Devices findByName(String name);

    List<Devices> findByUserId(long id);

    List<Devices> findByServesId(long id);

    List<Devices> findByUserIdAndStatus(long id, Status status);

    List<Devices> findByStatus(Status status);

    List<Devices> findByDeviceType(DeviceType type);

    List<Devices> findByStatusAndDeviceType(Status status, DeviceType type);
}
