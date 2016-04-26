package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.repository.GeoRepository;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * @author Balazs Hajdu
 */
@Repository
public class GeoIp2Repository implements GeoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoIp2Repository.class);

    @Inject
    DatabaseReader databaseReader;

    @Override
    public Optional<CityResponse> getUserLocation(String ipAddress) {
        CityResponse response = null;

        try {
            InetAddress ip = InetAddress.getByName(ipAddress);
            response = databaseReader.city(ip);

        } catch (UnknownHostException e) {
            LOGGER.error("Can not retrieve ip address: {}", ipAddress);
        } catch (GeoIp2Exception e) {
            LOGGER.warn("Can not find location for ip address: {}", ipAddress);
        } catch (IOException e) {
            LOGGER.error("Can not read database", e);
        }

        return Optional.ofNullable(response);
    }

}
