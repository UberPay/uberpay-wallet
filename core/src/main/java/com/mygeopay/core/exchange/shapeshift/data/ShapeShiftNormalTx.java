package com.mygeopay.core.exchange.shapeshift.data;

import com.mygeopay.core.coins.CoinID;
import com.mygeopay.core.coins.CoinType;
import com.mygeopay.core.exchange.shapeshift.ShapeShift;

import org.bitcoinj.core.Address;
import org.json.JSONObject;

/**
 * @author John L. Jegutanis
 */
public class ShapeShiftNormalTx extends ShapeShiftBase {
    public final String pair;
    public final Address deposit;
    public final Address withdrawal;

    public ShapeShiftNormalTx(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                deposit = new Address(CoinID.typeFromSymbol(data.getString("depositType")),
                        data.getString("deposit"));
                withdrawal = new Address(CoinID.typeFromSymbol(data.getString("withdrawalType")),
                        data.getString("withdrawal"));
                pair = ShapeShift.getPair((CoinType)deposit.getParameters(),
                        (CoinType)withdrawal.getParameters());
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            deposit = null;
            withdrawal = null;
            pair = null;
        }
    }
}
