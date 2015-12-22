package generated;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

public class FilterSmartphone extends AbstractTransformer{
	
	@Override
	protected Object doTransform(Object src, String enc)
			throws TransformerException {
		return isRelevant((Smartphone) src);
	}
	
    public boolean isRelevant(Smartphone smartphone){

    	// search the collected data for the screen dimensions
    	for(Smartphone.TechnicalData.Table table : smartphone.getTechnicalData().getTable()){
    		for( Smartphone.TechnicalData.Table.TableData tableData : table.getTableData() ){
    			
    			if(tableData.getDataName().equals("Tamanho do ecrã")){
    				
    				System.out.println("Data Value: " + tableData.getDataValue());
    				
    				//TODO:
    				//if( Integer.getInteger(tableData.getDataValue()) >= 10 ){
    					return true;
    				//}
    			}
    		}
    	}
    	
    	return false;
    }
	
}
