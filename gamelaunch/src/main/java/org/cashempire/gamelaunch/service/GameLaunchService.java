package org.cashempire.gamelaunch.service;

import lombok.AllArgsConstructor;
import org.cashempire.gamelaunch.dto.GameLaunchRequest;
import org.cashempire.gamelaunch.exception.GameLaunchException;
import org.cashempire.module.ClientLaunch;
import org.cashempire.repository.ClientLaunchRepository;
import org.cashempire.module.GameRevenueShare;
import org.cashempire.module.MiniGames;
import org.cashempire.module.Partner;
import org.cashempire.repository.GameRevenueShareRepository;
import org.cashempire.repository.MiniGamesRepository;
import org.cashempire.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class GameLaunchService {

    @Value("${banner.logo}")
    public String logoUrl;
    @Value("${launch.expire}")
    public String expire;
    public PartnerRepository partnerRepository;
    public GameRevenueShareRepository gameRevenueShareRepository;
    public MiniGamesRepository miniGamesRepository;
    public ClientLaunchRepository clientLaunchRepository;


    public String gameLaunch(GameLaunchRequest gameLaunchRequest, HttpServletRequest httpServletRequest) throws GameLaunchException {
        Partner partner;
        MiniGames miniGames;
        GameRevenueShare gameRevenueShare;
        partner = partnerRepository.findByGuid(gameLaunchRequest.getAgentId()).orElseThrow(() -> new GameLaunchException(""));
        miniGames = partnerValidation(partner, gameLaunchRequest, httpServletRequest.getRemoteUser());
        gameRevenueShare = GameRevenueShareValidation(miniGames,partner);
        ClientLaunch clientLaunch = new ClientLaunch(gameLaunchRequest.getPlayerId(), miniGames.getServerUrl(), gameLaunchRequest.getSessionKey(),gameLaunchRequest.getLanguage(),gameLaunchRequest.getCurrency(),gameLaunchRequest.isSound(),false, miniGames.getGameId(), partner.getPartnerId(), gameRevenueShare.getRevenuId(),expire);
        String token = clientLaunchRepository.save(clientLaunch).getToken();
        if(gameLaunchRequest.isLogo()) {
           return logoUrl.concat(miniGames.getClientUrl()).concat("?token=" + token);
        }else{
            return miniGames.getClientUrl().concat("?token=" + token);
        }
    }

    private GameRevenueShare GameRevenueShareValidation(MiniGames miniGames, Partner partner) throws GameLaunchException {
        GameRevenueShare gameRevenueShare = gameRevenueShareRepository.findByGameIdAndPartnerId(miniGames.getGameName(),partner.getPartnerId()).orElseThrow(()->new GameLaunchException("game is not assigned"));
        if(!gameRevenueShare.isStatus()){
            throw new GameLaunchException("game not active for this partner");
        }
        return gameRevenueShare;
    }

    private MiniGames partnerValidation(Partner partner, GameLaunchRequest gameLaunchRequest, String remoteUser) throws GameLaunchException {
        if (partner.isStatus()) {
            if (partner.getPartnerkey().equals(gameLaunchRequest.getAgentKey())) {
                if (partner.getPartnerIps().contains(remoteUser)) {
                    return minigamesValidate(gameLaunchRequest);
                } else {
                    throw new GameLaunchException("partner ips not whitelisted");
                }
            } else {
                throw new GameLaunchException("partner key incorrect");
            }
        } else {
            throw new GameLaunchException("partner not active");
        }
    }

    private MiniGames minigamesValidate(GameLaunchRequest gameLaunchRequest) throws GameLaunchException {
        MiniGames miniGames = miniGamesRepository.findByGuid(gameLaunchRequest.getGameId()).orElseThrow(()->new GameLaunchException("game id invalid"));
        if(!miniGames.isStatus()){
            throw new GameLaunchException();
        }
        return miniGames;
    }


}
