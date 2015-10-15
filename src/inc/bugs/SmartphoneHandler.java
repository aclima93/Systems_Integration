package inc.bugs;

import generated.Smartphone;
import generated2.SearchSmartphones.SmartphoneOverview;

/**
 * Created by aclima on 15/10/15.
 */
public class SmartphoneHandler {

    /**
     * Returns a simplified version of the Smartphone Object
     * @param smartphone more complex object
     * @return simplified smartphone
     */
    public SmartphoneOverview getSmartphoneOverviewFromSmartphone(Smartphone smartphone){

        SmartphoneOverview smartphoneOverview = new SmartphoneOverview();

        smartphoneOverview.setBrand(smartphone.getBrand());
        smartphoneOverview.setName(smartphone.getName());
        smartphoneOverview.setCurrency(smartphone.getCurrency());
        smartphoneOverview.setPrice(smartphone.getPrice());
        smartphoneOverview.setSmartphoneUrl(smartphone.getUrl());

        return smartphoneOverview;
    }

}
