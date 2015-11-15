package com.mygeopay.wallet;

import android.text.format.DateUtils;

import com.mygeopay.core.coins.BitcoinMain;
import com.mygeopay.core.coins.BitcoinTest;
import com.mygeopay.core.coins.BlackcoinMain;
import com.mygeopay.core.coins.BatacoinMain;
import com.mygeopay.core.coins.BolicoinMain;
import com.mygeopay.core.coins.CanadaeCoinMain;
import com.mygeopay.core.coins.CannacoinMain;
import com.mygeopay.core.coins.ClamcoinMain;
import com.mygeopay.core.coins.CoinID;
import com.mygeopay.core.coins.CoinType;
import com.mygeopay.core.coins.CryptoescudoMain;
import com.mygeopay.core.coins.DashMain;
import com.mygeopay.core.coins.DigibyteMain;
import com.mygeopay.core.coins.DigitalcoinMain;
import com.mygeopay.core.coins.DogecoinDarkMain;
import com.mygeopay.core.coins.DogecoinMain;
import com.mygeopay.core.coins.DogecoinTest;
import com.mygeopay.core.coins.FairCoinMain;
import com.mygeopay.core.coins.FeathercoinMain;
import com.mygeopay.core.coins.GcrCoinMain;
import com.mygeopay.core.coins.LitecoinMain;
import com.mygeopay.core.coins.LitecoinTest;
import com.mygeopay.core.coins.MazacoinMain;
import com.mygeopay.core.coins.NovacoinMain;
import com.mygeopay.core.coins.ParkbyteMain;
import com.mygeopay.core.coins.ShadowCashMain;
import com.mygeopay.core.coins.StartcoinMain;
import com.mygeopay.core.coins.MonacoinMain;
import com.mygeopay.core.coins.NamecoinMain;
import com.mygeopay.core.coins.NuBitsMain;
import com.mygeopay.core.coins.NuSharesMain;
import com.mygeopay.core.coins.PeercoinMain;
import com.mygeopay.core.coins.ReddcoinMain;
import com.mygeopay.core.coins.RubycoinMain;
import com.mygeopay.core.coins.StepscoinMain;
import com.mygeopay.core.coins.UroMain;
import com.mygeopay.core.coins.NeoscoinMain;
import com.mygeopay.core.coins.JumbucksMain;
import com.mygeopay.core.coins.VcoinMain;
import com.mygeopay.core.coins.VertcoinMain;
import com.mygeopay.core.coins.ViacoinMain;
import com.mygeopay.core.coins.VpncoinMain;
import com.mygeopay.core.coins.OkCashMain;
import com.mygeopay.core.network.CoinAddress;
import com.mygeopay.stratumj.ServerAddress;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author John L. Jegutanis
 * @author Andreas Schildbach
 */
public class Constants {

    public static final int SEED_ENTROPY_DEFAULT = 192;
    public static final int SEED_ENTROPY_EXTRA = 256;

    public static final String ARG_SEED = "seed";
    public static final String ARG_PASSWORD = "password";
    public static final String ARG_SEED_PASSWORD = "seed_password";
    public static final String ARG_EMPTY_WALLET = "empty_wallet";
    public static final String ARG_SEND_TO_ADDRESS = "send_to_address";
    public static final String ARG_SEND_TO_COIN_TYPE = "send_to_coin_type";
    public static final String ARG_SEND_TO_ACCOUNT_ID = "send_to_account_id";
    public static final String ARG_SEND_VALUE = "send_value";
    public static final String ARG_COIN_ID = "coin_id";
    public static final String ARG_ACCOUNT_ID = "account_id";
    public static final String ARG_MULTIPLE_COIN_IDS = "multiple_coin_ids";
    public static final String ARG_MULTIPLE_CHOICE = "multiple_choice";
    public static final String ARG_TRANSACTION_ID = "transaction_id";
    public static final String ARG_ERROR = "error";
    public static final String ARG_MESSAGE = "message";
    public static final String ARG_ADDRESS = "address";
    public static final String ARG_ADDRESS_STRING = "address_string";
    public static final String ARG_EXCHANGE_ENTRY = "exchange_entry";
    public static final String ARG_TEST_WALLET = "test_wallet";
    public static final String ARG_URI = "test_wallet";

    public static final String WALLET_FILENAME_PROTOBUF = "wallet";
    public static final long WALLET_WRITE_DELAY = 5;
    public static final TimeUnit WALLET_WRITE_DELAY_UNIT = TimeUnit.SECONDS;

    public static final long STOP_SERVICE_AFTER_IDLE_SECS = 30 * 60; // 30 mins

    public static final String HTTP_CACHE_DIR = "http_cache";
    public static final int HTTP_CACHE_SIZE = 256 * 1024; // 256 KiB
    public static final int HTTP_TIMEOUT_MS = 15 * (int) DateUtils.SECOND_IN_MILLIS;

    public static final long RATE_UPDATE_FREQ_MS = 30 * DateUtils.SECOND_IN_MILLIS;

    /** Default currency to use if all default mechanisms fail. */
    public static final String DEFAULT_EXCHANGE_CURRENCY = "USD";

    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Charset US_ASCII = Charset.forName("US-ASCII");

    public static final char CHAR_HAIR_SPACE = '\u200a';
    public static final char CHAR_THIN_SPACE = '\u2009';
    public static final char CHAR_ALMOST_EQUAL_TO = '\u2248';
    public static final char CHAR_CHECKMARK = '\u2713';
    public static final char CURRENCY_PLUS_SIGN = '+';
    public static final char CURRENCY_MINUS_SIGN = '-';

    public static final String MARKET_APP_URL = "market://details?id=%s";
    public static final String BINARY_URL = "https://github.com/UberPay/uberpay-wallet/releases";

    public static final String VERSION_URL = "http://uberpay.io/version";

    // TODO move to resource files
    public static final List<CoinAddress> DEFAULT_COINS_SERVERS = ImmutableList.of(
            new CoinAddress(BitcoinMain.get(),      new ServerAddress("btc-cce-1.coinomi.net", 5001),
                                                    new ServerAddress("btc-cce-2.coinomi.net", 5001)),
            new CoinAddress(BitcoinTest.get(),      new ServerAddress("btc-testnet-cce-1.coinomi.net", 15001),
                                                    new ServerAddress("btc-testnet-cce-2.coinomi.net", 15001)),
            new CoinAddress(DogecoinMain.get(),     new ServerAddress("doge-cce-1.coinomi.net", 5003),
                                                    new ServerAddress("doge-cce-2.coinomi.net", 5003)),
            new CoinAddress(DogecoinTest.get(),     new ServerAddress("doge-testnet-cce-1.coinomi.net", 15003),
                                                    new ServerAddress("doge-testnet-cce-2.coinomi.net", 15003)),
            new CoinAddress(LitecoinMain.get(),     new ServerAddress("ltc-cce-1.coinomi.net", 5002),
                                                    new ServerAddress("ltc-cce-2.coinomi.net", 5002)),
            new CoinAddress(LitecoinTest.get(),     new ServerAddress("ltc-testnet-cce-1.coinomi.net", 15002),
                                                    new ServerAddress("ltc-testnet-cce-2.coinomi.net", 15002)),
            new CoinAddress(PeercoinMain.get(),     new ServerAddress("ppc-cce-1.coinomi.net", 5004),
                                                    new ServerAddress("ppc-cce-2.coinomi.net", 5004)),
            new CoinAddress(NuSharesMain.get(),     new ServerAddress("nsr-cce-1.coinomi.net", 5011),
                                                    new ServerAddress("nsr-cce-2.coinomi.net", 5011)),
            new CoinAddress(NuBitsMain.get(),       new ServerAddress("nbt-cce-1.coinomi.net", 5012),
                                                    new ServerAddress("nbt-cce-2.coinomi.net", 5012)),
            new CoinAddress(DashMain.get(),         new ServerAddress("drk-cce-1.coinomi.net", 5013),
                                                    new ServerAddress("drk-cce-2.coinomi.net", 5013)),
            new CoinAddress(ReddcoinMain.get(),     new ServerAddress("rdd-cce-1.coinomi.net", 5014),
                                                    new ServerAddress("rdd-cce-2.coinomi.net", 5014)),
            new CoinAddress(BlackcoinMain.get(),    new ServerAddress("blk-cce-1.coinomi.net", 5015),
                                                    new ServerAddress("blk-cce-2.coinomi.net", 5015)),
            new CoinAddress(NamecoinMain.get(),     new ServerAddress("nmc-cce-1.coinomi.net", 5016),
                                                    new ServerAddress("nmc-cce-2.coinomi.net", 5016)),
            new CoinAddress(FeathercoinMain.get(),  new ServerAddress("ftc-cce-1.coinomi.net", 5017),
                                                    new ServerAddress("ftc-cce-2.coinomi.net", 5017)),
            new CoinAddress(RubycoinMain.get(),     new ServerAddress("rby-cce-1.coinomi.net", 5018),
                                                    new ServerAddress("rby-cce-2.coinomi.net", 5018)),
            new CoinAddress(UroMain.get(),          new ServerAddress("uro-cce-1.coinomi.net", 5019),
                                                    new ServerAddress("uro-cce-2.coinomi.net", 5019)),
            new CoinAddress(DigitalcoinMain.get(),  new ServerAddress("dgc-cce-1.coinomi.net", 5020),
                                                    new ServerAddress("dgc-cce-2.coinomi.net", 5020)),
            new CoinAddress(CannacoinMain.get(),    new ServerAddress("ccn-cce-1.coinomi.net", 5021),
                                                    new ServerAddress("ccn-cce-2.coinomi.net", 5021)),
            new CoinAddress(MonacoinMain.get(),     new ServerAddress("mona-cce-1.coinomi.net", 5022),
                                                    new ServerAddress("mona-cce-2.coinomi.net", 5022)),
            new CoinAddress(DigibyteMain.get(),     new ServerAddress("dgb-cce-1.coinomi.net", 5023),
                                                    new ServerAddress("dgb-cce-2.coinomi.net", 5023)),
            new CoinAddress(MazacoinMain.get(),     new ServerAddress("tate.cryptoadhd.com", 50001),
                                                    new ServerAddress("tate.maza.club", 50001)),
            new CoinAddress(CryptoescudoMain.get(), new ServerAddress("electrum1.cryptoescudo.com.br", 50001),
                                                    new ServerAddress("electrum1.cryptoescudo.org", 50001)),
            // TODO Correct Electrum URL for CRYPTOESCUDO
            new CoinAddress(StartcoinMain.get(),    new ServerAddress("node1.startcoin.org", 9247),
                                                    new ServerAddress("node2.startcoin.org", 9247)),
            new CoinAddress(ShadowCashMain.get(),   new ServerAddress("sdc-cce-1.coinomi.net", 5026),
                                                    new ServerAddress("sdc-cce-2.coinomi.net", 5026)),
            new CoinAddress(ParkbyteMain.get(),     new ServerAddress("pkb-cce-1.coinomi.net", 5035),
                                                    new ServerAddress("pkb-cce-2.coinomi.net", 5035)),
            new CoinAddress(VcoinMain.get(),        new ServerAddress("vcn.btcnetwork.info", 50001)),
            // TODO Correct Electrum URL for VCOIN
            new CoinAddress(ViacoinMain.get(),      new ServerAddress("vialectrum.viacoin.net", 50001),
                                                    new ServerAddress("server.vialectrum.org", 50001)),
            //
            new CoinAddress(OkCashMain.get(),       new ServerAddress("ok.el.0.btcnetwork.info", 5040),  //162.248.142.89
                                                    new ServerAddress("ok.el.1.btcnetwork.info", 5040)),  //167.88.40.231
            // 192.99.35.69:9247 # node1.startcoin.org
            // 37.59.54.5:9247 # node2.startcoin.org
            // 5024 primecoin
            // 5025 clams
            // 5026 shadowcoin
            new CoinAddress(NeoscoinMain.get(),     new ServerAddress("neos-cce-1.coinomi.net", 5027),
                                                    new ServerAddress("neos-cce-2.coinomi.net", 5027)),
            new CoinAddress(VertcoinMain.get(),     new ServerAddress("vtc-cce-1.coinomi.net", 5028),
                                                    new ServerAddress("vtc-cce-2.coinomi.net", 5028)),
            new CoinAddress(JumbucksMain.get(),     new ServerAddress("jbs-cce-1.coinomi.net", 5029),
                                                    new ServerAddress("jbs-cce-2.coinomi.net", 5029)),
            new CoinAddress(CanadaeCoinMain.get(),  new ServerAddress("cdn-cce-1.coinomi.net", 5033),
                                                    new ServerAddress("cdn-cce-2.coinomi.net", 5033)),
            //new CoinAddress(ClamcoinMain.get(),     new ServerAddress("clam.el.0.btcnetwork.info", 50001)),
            new CoinAddress(ClamcoinMain.get(),     new ServerAddress("clam-cce-2.coinomi.net", 5025),
                                                    new ServerAddress("clam-cce-1.coinomi.net", 5025)),
            // TODO Correct electrum URL for CLAM
            new CoinAddress(DogecoinDarkMain.get(), new ServerAddress("electrum-doged.space", 50001),
            // SSL
            // new CoinAddress(DogecoinDarkTest.get(), new ServerAddress("electrum-doged.space", 50002),
                                                    new ServerAddress("doged.el.0.btcnetwork.info", 50001)), // 104.236.133.57
            // TODO Correct electrum URL from DOGED
            new CoinAddress(NovacoinMain.get(),     new ServerAddress("nvc-cce-1.coinomi.net", 5034),
                                                    new ServerAddress("nvc-cce-2.coinomi.net", 5034)),
            //
            new CoinAddress(GcrCoinMain.get(),      new ServerAddress("gcr-cce-1.coinomi.net", 5038),
                                                    new ServerAddress("gcr-cce-2.coinomi.net", 5038)),
            //
            new CoinAddress(BatacoinMain.get(),     new ServerAddress("104.236.239.154", 50001)),
            //
            new CoinAddress(StepscoinMain.get(),    new ServerAddress("192.241.216.229", 50001)),
            //                                        new ServerAddress("192.241.216.229", 50002)),
            new CoinAddress(BolicoinMain.get(),  new ServerAddress("electrum-boli.xyz", 50001)),
            // TODO pending BOLI Electrum test
            //
            new CoinAddress(VpncoinMain.get(),      new ServerAddress("vpn-cce-1.coinomi.net", 5032),
                                                    new ServerAddress("vpn-cce-2.coinomi.net", 5032)),
            // FairCoin
             new CoinAddress(FairCoinMain.get(),     new ServerAddress("electrum.fair.to", 51001))
            // SSL
            // new CoinAddress(FairCoinMain.get(),     new ServerAddress("electrum.fair.to", 51002))
    );

    public static final HashMap<CoinType, Integer> COINS_ICONS;
    public static final HashMap<CoinType, String> COINS_BLOCK_EXPLORERS;
    static {
        COINS_ICONS = new HashMap<>();
        COINS_ICONS.put(CoinID.BITCOIN_MAIN.getCoinType(), R.drawable.bitcoin);
        COINS_ICONS.put(CoinID.BITCOIN_TEST.getCoinType(), R.drawable.bitcoin_test);
        COINS_ICONS.put(CoinID.DOGECOIN_MAIN.getCoinType(), R.drawable.dogecoin);
        COINS_ICONS.put(CoinID.DOGECOIN_TEST.getCoinType(), R.drawable.dogecoin_test);
        COINS_ICONS.put(CoinID.DOGECOINDARK_MAIN.getCoinType(), R.drawable.doged);
        COINS_ICONS.put(CoinID.GCRCOIN_MAIN.getCoinType(), R.drawable.gcrcoin);
        COINS_ICONS.put(CoinID.BATACOIN_MAIN.getCoinType(), R.drawable.bta);
        COINS_ICONS.put(CoinID.LITECOIN_MAIN.getCoinType(), R.drawable.litecoin);
        COINS_ICONS.put(CoinID.LITECOIN_TEST.getCoinType(), R.drawable.litecoin_test);
        COINS_ICONS.put(CoinID.PEERCOIN_MAIN.getCoinType(), R.drawable.peercoin);
        COINS_ICONS.put(CoinID.DASH_MAIN.getCoinType(), R.drawable.dash);
        COINS_ICONS.put(CoinID.REDDCOIN_MAIN.getCoinType(), R.drawable.reddcoin);
        COINS_ICONS.put(CoinID.NUSHARES_MAIN.getCoinType(), R.drawable.nushares);
        COINS_ICONS.put(CoinID.NUBITS_MAIN.getCoinType(), R.drawable.nubits);
        COINS_ICONS.put(CoinID.BLACKCOIN_MAIN.getCoinType(), R.drawable.blackcoin);
        COINS_ICONS.put(CoinID.MONACOIN_MAIN.getCoinType(), R.drawable.monacoin);
        COINS_ICONS.put(CoinID.RUBYCOIN_MAIN.getCoinType(), R.drawable.rubycoin);
        COINS_ICONS.put(CoinID.NAMECOIN_MAIN.getCoinType(), R.drawable.namecoin);
        COINS_ICONS.put(CoinID.FEATHERCOIN_MAIN.getCoinType(), R.drawable.feathercoin);
        COINS_ICONS.put(CoinID.CANADAECOIN_MAIN.getCoinType(), R.drawable.canadaecoin);
        COINS_ICONS.put(CoinID.SHADOWCASH_MAIN.getCoinType(), R.drawable.shadowcash);
        COINS_ICONS.put(CoinID.CLAMCOIN_MAIN.getCoinType(), R.drawable.clam);
        COINS_ICONS.put(CoinID.PARKBYTE_MAIN.getCoinType(), R.drawable.parkbyte);
        COINS_ICONS.put(CoinID.CRYPTOESCUDO_MAIN.getCoinType(), R.drawable.cryptoescudo);
        COINS_ICONS.put(CoinID.OKCASHCOIN_MAIN.getCoinType(), R.drawable.okcash);
//        COINS_ICONS.put(CoinID.URO_MAIN.getCoinType(), R.drawable.uro);
        COINS_ICONS.put(CoinID.MAZACOIN_MAIN.getCoinType(), R.drawable.mazacoin);
        COINS_ICONS.put(CoinID.STARTCOIN_MAIN.getCoinType(), R.drawable.startcoin);
        COINS_ICONS.put(CoinID.DIGITALCOIN_MAIN.getCoinType(), R.drawable.digitalcoin);
        COINS_ICONS.put(CoinID.CANNACOIN_MAIN.getCoinType(), R.drawable.cannacoin);
        COINS_ICONS.put(CoinID.DIGIBYTE_MAIN.getCoinType(), R.drawable.digibyte);
        COINS_ICONS.put(CoinID.NEOSCOIN_MAIN.getCoinType(), R.drawable.neoscoin);
        COINS_ICONS.put(CoinID.VERTCOIN_MAIN.getCoinType(), R.drawable.vertcoin);
        COINS_ICONS.put(CoinID.JUMBUCKS_MAIN.getCoinType(), R.drawable.jumbucks);
        COINS_ICONS.put(CoinID.VPNCOIN_MAIN.getCoinType(), R.drawable.vpncoin);
        COINS_ICONS.put(CoinID.VCOIN_MAIN.getCoinType(),  R.drawable.vcoin);
        COINS_ICONS.put(CoinID.NOVACOIN_MAIN.getCoinType(), R.drawable.novacoin);
        COINS_ICONS.put(CoinID.VIACOIN_MAIN.getCoinType(), R.drawable.viacoin);
        COINS_ICONS.put(CoinID.BOLICOIN_MAIN.getCoinType(), R.drawable.bolivar);
        COINS_ICONS.put(CoinID.STEPSCOIN_MAIN.getCoinType(), R.drawable.steps);
        COINS_ICONS.put(CoinID.FAIRCOIN_MAIN.getCoinType(), R.drawable.faircoin);

        COINS_BLOCK_EXPLORERS = new HashMap<CoinType, String>();
        COINS_BLOCK_EXPLORERS.put(CoinID.BITCOIN_MAIN.getCoinType(), "https://blockchain.info/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.BITCOIN_TEST.getCoinType(), "https://chain.so/tx/BTCTEST/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.DOGECOIN_MAIN.getCoinType(), "https://chain.so/tx/DOGE/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.DOGECOIN_TEST.getCoinType(), "https://chain.so/tx/DOGETEST/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.GCRCOIN_MAIN.getCoinType(), "https://chainz.cryptoid.info/gcr/tx.dws?%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.LITECOIN_MAIN.getCoinType(), "http://ltc.blockr.io/tx/info/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.LITECOIN_TEST.getCoinType(), "https://chain.so/tx/LTCTEST/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.PEERCOIN_MAIN.getCoinType(), "http://ppc.blockr.io/tx/info/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.DASH_MAIN.getCoinType(), "https://bitinfocharts.com/darkcoin/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.NUSHARES_MAIN.getCoinType(), "http://blockexplorer.nu/transactions/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.NUBITS_MAIN.getCoinType(), "http://blockexplorer.nu/transactions/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.REDDCOIN_MAIN.getCoinType(), "http://live.reddcoin.com/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.BLACKCOIN_MAIN.getCoinType(), "https://chainz.cryptoid.info/blk/tx.dws?%s.htm");
        COINS_BLOCK_EXPLORERS.put(CoinID.CLAMCOIN_MAIN.getCoinType(), "http://clamsight.com/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.MONACOIN_MAIN.getCoinType(), "http://abe.monash.pw:3000/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.RUBYCOIN_MAIN.getCoinType(), "https://chainz.cryptoid.info/rby/tx.dws?%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.NAMECOIN_MAIN.getCoinType(), "https://explorer.namecoin.info/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.FEATHERCOIN_MAIN.getCoinType(), "http://explorer.feathercoin.com/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.SHADOWCASH_MAIN.getCoinType(),  "http://shadowchain.info/tx/%s");
//      COINS_BLOCK_EXPLORERS.put(CoinID.URO_MAIN.getCoinType(), "https://chainz.cryptoid.info/uro/tx.dws?%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.MAZACOIN_MAIN.getCoinType(), "https://mazacha.in/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.STARTCOIN_MAIN.getCoinType(), "http://explorer.startcoin.org/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.CANADAECOIN_MAIN.getCoinType(), "http://explorer.canadaecoin.ca/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.DIGITALCOIN_MAIN.getCoinType(), "https://chainz.cryptoid.info/dgc/tx.dws?%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.PARKBYTE_MAIN.getCoinType(), "https://chainz.cryptoid.info/pkb/tx.dws?%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.CANNACOIN_MAIN.getCoinType(), "https://chainz.cryptoid.info/ccn/tx.dws?%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.DIGIBYTE_MAIN.getCoinType(), "https://digiexplorer.info/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.NEOSCOIN_MAIN.getCoinType(), "http://explorer.infernopool.com/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.VERTCOIN_MAIN.getCoinType(), "https://bitinfocharts.com/vertcoin/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.JUMBUCKS_MAIN.getCoinType(), "http://explorer.getjumbucks.com/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.VPNCOIN_MAIN.getCoinType(), "https://blockexperts.com/vpn/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.CRYPTOESCUDO_MAIN.getCoinType(), "http://explorador.cryptoescudo.net/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.VCOIN_MAIN.getCoinType(), "https://chainz.cryptoid.info/vcn/tx.dws?%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.DOGECOINDARK_MAIN.getCoinType(), "https://www.blockexperts.com/doged/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.BATACOIN_MAIN.getCoinType(), "https://chainz.cryptoid.info/bta/tx.dws?%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.NOVACOIN_MAIN.getCoinType(), "http://explorer.novaco.in/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.VIACOIN_MAIN.getCoinType(), "http://explorer.viacoin.org/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.STEPSCOIN_MAIN.getCoinType(), "http://chain.altcoinsteps.com/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.OKCASHCOIN_MAIN.getCoinType(), "https://chainz.cryptoid.info/ok/tx.dws?%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.FAIRCOIN_MAIN.getCoinType(), "https://chain.fair-coin.org/tx/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.BOLICOIN_MAIN.getCoinType(),  "https://chainz.cryptoid.info/boli/tx.dws?%s");
    }

    public static final CoinType DEFAULT_COIN = BitcoinMain.get();
    public static final List<CoinType> DEFAULT_COINS = ImmutableList.of((CoinType) BitcoinMain.get());
    public static final ArrayList<String> DEFAULT_TEST_COIN_IDS = Lists.newArrayList(
            BitcoinTest.get().getId(),
            LitecoinTest.get().getId(),
            DogecoinTest.get().getId()
    );

    public static final List<CoinType> SUPPORTED_COINS = ImmutableList.of(
            BitcoinMain.get(),
            DogecoinMain.get(),
            LitecoinMain.get(),
            DogecoinDarkMain.get(),
/*            BatacoinMain.get(), */
            FairCoinMain.get(),
            ViacoinMain.get(),
            MazacoinMain.get(),
/*            BolicoinMain.get(), */
            CanadaeCoinMain.get(),
            OkCashMain.get(),
/*            StartcoinMain.get(), */
            PeercoinMain.get(),
            DashMain.get(),
            NamecoinMain.get(),
            BlackcoinMain.get(),
            ClamcoinMain.get(),
            MonacoinMain.get(),
            NovacoinMain.get(),
            NuSharesMain.get(),
            VpncoinMain.get(),
            ReddcoinMain.get(),
            VertcoinMain.get(),
            FeathercoinMain.get(),
            RubycoinMain.get(),
            DigibyteMain.get(),
            DigitalcoinMain.get(),
            JumbucksMain.get(),
            NeoscoinMain.get(),
            CannacoinMain.get(),
            ShadowCashMain.get(),
            StepscoinMain.get(),

            NuBitsMain.get(),
/*            VcoinMain.get(), */
            ParkbyteMain.get(),
            GcrCoinMain.get(),
/*            CryptoescudoMain.get(), */
            BitcoinTest.get(),
            LitecoinTest.get(),
            DogecoinTest.get()
    );
}
