package umc.team4.apiPayload.exception.handler;

import umc.team4.apiPayload.code.BaseErrorCode;
import umc.team4.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {
    public TempHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
