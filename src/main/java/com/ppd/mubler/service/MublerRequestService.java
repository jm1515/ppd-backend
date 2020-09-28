package com.ppd.mubler.service;

import com.ppd.mubler.model.entity.MublerRequest;
import com.ppd.mubler.model.entity.User;
import com.ppd.mubler.model.entity.VehiculeType;
import com.ppd.mubler.model.entity.exceptions.InvalidMublerRequestException;
import com.ppd.mubler.model.entity.log4j.MublerLogger;
import com.ppd.mubler.model.repository.MublerRequestRepository;
import com.ppd.mubler.model.repository.VehiculeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MublerRequestService implements Service<MublerRequest>{

    private static final String MUBLER_LARGE = "L";
    private static final String MUBLER_SMALL = "S";
    private static final String MUBLER_XLARGE = "XL";

    @Autowired
    MublerRequestRepository mublerRequestRepository;

    @Autowired
    VehiculeTypeRepository vehiculeTypeRepository;

    @Autowired
    DialogService dialogService;

    @Autowired
    private UserService userService;

    @Override
    public void save(MublerRequest entity) {
        mublerRequestRepository.save(entity);
    }

    private MublerLogger mublerLogger = MublerLogger.getInstance();

    public void generateRequestPrice(MublerRequest mublerRequest) throws InvalidMublerRequestException{
        if(!this.assertRequestIsValid(mublerRequest)){
            throw new InvalidMublerRequestException();
        }
        VehiculeType requestVehiculeType = findRequestVehiculeType(mublerRequest);
        float fakePrice;
        if (requestVehiculeType == null){
            throw new InvalidMublerRequestException();
        }
        switch (requestVehiculeType.getName()){
            case MUBLER_XLARGE:
                fakePrice = 50f;
                mublerRequest.setPrice(fakePrice);
                break;
            case MUBLER_LARGE:
                fakePrice = 30f;
                mublerRequest.setPrice(fakePrice);
                break;
            case MUBLER_SMALL:
                fakePrice = 15f;
                mublerRequest.setPrice(fakePrice);
                break;
        }
    }

    private VehiculeType findRequestVehiculeType(MublerRequest mublerRequest){
        for (VehiculeType vehiculeType : vehiculeTypeRepository.findAll()) {
            if (vehiculeType.getName().equals(mublerRequest.getVehiculeTypeName())){
                return vehiculeType;
            }
        }
        return null;
    }

    public void setTimestamp(long timestamp, MublerRequest mublerRequest){
        mublerRequest.setTimestamp(new Date(timestamp));
    }

    private boolean assertRequestIsValid(MublerRequest mublerRequest){
        return (mublerRequest.getVehiculeTypeName() != null) && (mublerRequest.getTimestamp() != null);
    }

    public List<MublerRequest> getMublerRequestsByIdRequester(long idRequester){
        return this.mublerRequestRepository.findByIdRequester(idRequester);
    }

    public List<MublerRequest> getMublerRequestsByIdAccepter(long idAccepter){
        return this.mublerRequestRepository.findByIdAccepter(idAccepter);
    }

    public MublerRequest getMublerRequestById(long idRequest){
        return this.mublerRequestRepository.findById(idRequest);
    }

    public void acceptRequest(long idMubler, long idRequest){
        mublerLogger.logInfo(idMubler + "  trying to accept " + idRequest);
        User user = userService.getUserById(idMubler);
        if (user != null){
            MublerRequest mublerRequest = mublerRequestRepository.findById(idRequest);
            if (mublerRequest != null) {
                mublerLogger.logInfo(idMubler + "  accepeted " + idRequest);
                mublerLogger.logInfo("mubler request = " + mublerRequest);

                mublerRequest.setIdAccepter(idMubler);
                mublerRequestRepository.save(mublerRequest);
                User infos = userService.getUserById(idMubler);
                mublerLogger.logInfo("user infos = " + infos);
                dialogService.sendMessage(mublerRequest.getIdRequester(), "REQUEST_ACCEPTED/" + infos.getId()
                        + "/" + infos.getFirstName()
                        + "/" + infos.getLastName()
                        + "/" + infos.getPhoneNumber());
            }else {
                mublerLogger.logInfo("request not found");
            }
        } else {
            mublerLogger.logInfo("mubler not found");
        }
    }

    public void endRequest(long idRequest){
        mublerLogger.logInfo("Ending request : " + idRequest);
        MublerRequest mublerRequest = mublerRequestRepository.findById(idRequest);
        if (mublerRequest != null) {
            mublerRequest.setOver(true);
            mublerRequestRepository.save(mublerRequest);
            dialogService.sendMessage(mublerRequest.getIdRequester(), "REQUEST_OVER");
        }
    }

    public void cancelRequest(long idRequest){
        mublerLogger.logInfo("Ending request : " + idRequest);
        MublerRequest mublerRequest = mublerRequestRepository.findById(idRequest);
        if (mublerRequest != null) {
            mublerRequest.setOver(true);
            mublerRequest.setPrice(0);
            mublerRequestRepository.save(mublerRequest);
            dialogService.sendMessage(mublerRequest.getIdRequester(), "REQUEST_CANCELED");
        }
    }

}
