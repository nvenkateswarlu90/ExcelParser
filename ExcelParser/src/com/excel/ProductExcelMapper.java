package com.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import com.a4.product.beans.Artwork;
import com.a4.product.beans.Color;
import com.a4.product.beans.ImprintMethod;
import com.a4.product.beans.Inventory;
import com.a4.product.beans.Personalization;
import com.a4.product.beans.Product;
import com.a4.product.beans.ProductConfigurations;
import com.a4.product.beans.ProductSkus;
import com.a4.product.beans.ProductionTime;
import com.a4.product.beans.RushTime;
import com.a4.product.beans.SameDayRush;
import com.a4.product.beans.Samples;
import com.a4.product.beans.Shape;
import com.criteria.parser.ProductArtworkProcessor;
import com.criteria.parser.ProductColorParser;
import com.criteria.parser.ProductImprintMethodParser;
import com.criteria.parser.ProductOriginParser;
import com.criteria.parser.ProductPackagingParser;
import com.criteria.parser.ProductRushTimeParser;
import com.criteria.parser.ProductSameDayParser;
import com.criteria.parser.ProductSampleParser;
import com.criteria.parser.ProductShapeParser;
import com.criteria.parser.ProductSizeParser;
import com.criteria.parser.ProductThemeParser;
import com.criteria.parser.ProductTradeNameParser;
import com.criteria.parser.ProductionTimeParser;
import com.criteria.parser.ProductSkuParser;
import com.a4.product.beans.ShippingEstimate;
import com.a4.product.beans.Size;
import com.criteria.parser.PersonlizationParser;
import com.criteria.parser.ShippingEstimationParser;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ProductExcelMapper {
	
	private   Logger              _LOGGER              = Logger.getLogger(getClass());
	
	public static void main(String[] args)  {
		try
		{
		ProductExcelMapper djvsd= new ProductExcelMapper();
		List<Product> BeanObj = djvsd.readFileUsingPOI("D:\\A4 ESPUpdate\\Excel File\\v2\\productv2.xlsx");
		if(BeanObj==null){
			// log error or write business logic
			System.out.println("there was an error while prcessing this file");
		}else{
			System.out.println("send for posting to asi");
		}
		}catch(Exception e){
			
		}
	}
	
	
	public  synchronized List<Product> readFileUsingPOI(String path)   {
		List<Product>   productList = new ArrayList<Product>();
		//_LOGGER.error("Error while Processing excel sheet :"+path.substring(path.lastIndexOf("\\")).replaceAll("\\","") );
		_LOGGER.info("Reading Excel file from File Path"+path.substring(path.lastIndexOf("\\")) );
         //System.out.println(path.length());
		//System.out.println(path.length()-1);
		//int b=path.length()-1;
        //System.out.println(path.substring(path.lastIndexOf("\\")));
		//System.out.println(path.substring(path.lastIndexOf("'\'"), b));
		
         FileInputStream inputStream = null;
         Workbook workbook = null;
		try{
			  inputStream = new FileInputStream(new File(path));
			_LOGGER.info("Completed Reading Excel file from File Path");
			_LOGGER.info("Creating & Initializing Workbook");
			  workbook = WorkbookFactory.create(new File(path));//new XSSFWorkbook(inputStream);
			_LOGGER.info("Workbook Object created");
			  Product productExcelObj = new Product();
			  ProductConfigurations productConfigObj=new ProductConfigurations();
	    _LOGGER.info("Processing WorkSheet");
	    Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		_LOGGER.info("Started Processing Product");
		while (iterator.hasNext()) {
			
			try{
			Row nextRow = iterator.next();
			if (nextRow.getRowNum() == 0)
				continue;
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			
			RushTime rushTime =new RushTime();
			SameDayRush sameDayObj=new SameDayRush();
			Samples samples=new Samples();
			
			List<Color> color = new ArrayList<Color>();
			List<String> origin = new ArrayList<String>();
			List<String> lineNames = new ArrayList<String>();
			List<String> categories = new ArrayList<String>();
			List<String> productKeywords = new ArrayList<String>();
			List<String> complianceCerts = new ArrayList<String>();
			List<String> safetyWarnings = new ArrayList<String>();
			List<Personalization> personalizationlist = new ArrayList<Personalization>();
			List<String> packaging = new ArrayList<String>();
			List<String> themes = new ArrayList<String>();
			List<String> tradeName = new ArrayList<String>();
			List<ImprintMethod> imprintMethods = new ArrayList<ImprintMethod>();
			List<Artwork> artworkList = new ArrayList<Artwork>();
			List<Shape> shapeList=new ArrayList<Shape>();
			List<ProductionTime> productionTimeList = new ArrayList<ProductionTime>();
			
			ProductColorParser colorparser=new ProductColorParser();
			ProductOriginParser originParser=new ProductOriginParser();
			ProductRushTimeParser rushTimeParser=new ProductRushTimeParser();
			ProductSameDayParser sameDayParser=new ProductSameDayParser();
			ProductSampleParser sampleParser =new ProductSampleParser();
			PersonlizationParser personalizationParser=new PersonlizationParser();
			ShippingEstimationParser shipinestmt = new ShippingEstimationParser();
			ProductSizeParser sizeParser=new ProductSizeParser();
			ProductPackagingParser packagingParser=new ProductPackagingParser();
			ProductTradeNameParser tradeNameParser=new ProductTradeNameParser();
			ProductImprintMethodParser imprintMethodParser=new ProductImprintMethodParser();
			ProductArtworkProcessor artworkProcessor=new ProductArtworkProcessor();
			ProductShapeParser shapeParser=new ProductShapeParser();
			ProductionTimeParser productionTimeParser =new ProductionTimeParser();
			ProductThemeParser themeParser=new ProductThemeParser();
			//ProductConfigurations productConfigObj=new ProductConfigurations();
			Inventory inventoryObj = new Inventory();
	        Size sizeObj = new Size();
			ShippingEstimate ShipingItem = new ShippingEstimate();
			List<ProductSkus> productsku=new ArrayList<ProductSkus>();
			ProductSkus skuObj= new ProductSkus();
			ProductSkuParser skuparserobj=new ProductSkuParser();
			
		
			
			String shippingitemValue = null;
			String shippingdimensionValue = null;
			String sizeGroup=null;
			String rushService=null;
			String prodSample=null;
			String SKUCriteria1 =null;
			String SKUCriteria2 =null;
			String skuvalue  =null;
			String Inlink  =null;
			String Instatus  =null;
		
			
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				int columnIndex = cell.getColumnIndex();

				switch (columnIndex + 1) {
				case 1:
					String externalProductId = cell.getStringCellValue();
					_LOGGER.info("Processing Product :"+ externalProductId);
					/*if(externalProductId==null || externalProductId.isEmpty()){
						rowFlag=true;
						break;
					}*/
					productExcelObj.setExternalProductId(externalProductId);
					// //System.out.println("external id is " +externalProductId);
					//System.out.println("case 1");
					 
					break;
					
				case 2:
					String name = cell.getStringCellValue();
					
					if(!StringUtils.isEmpty(name)){
					productExcelObj.setName(cell.getStringCellValue());
					}else{
						productExcelObj.setName(ApplicationConstants.CONST_STRING_EMPTY);
					}
					// //System.out.println("product name is " +name);
					//System.out.println("case 2");
					break;
				case 3:
					int asiProdNo = (int) cell.getNumericCellValue();
					productExcelObj.setAsiProdNo(Integer.toString(asiProdNo));
					break;
				case 4:
					String productLevelSku = cell.getStringCellValue();
					if(!StringUtils.isEmpty(productLevelSku)){
					productExcelObj.setProductLevelSku(productLevelSku);
					// //System.out.println("sku is " +productLevelSku);
					}else{
						productExcelObj.setProductLevelSku(ApplicationConstants.CONST_STRING_EMPTY);
					}
					//System.out.println("case 4");
					break;
					
				case 5:
					String inventoryLink = cell.getStringCellValue();
					if(!StringUtils.isEmpty(inventoryLink)){
					inventoryObj.setInventoryLink(inventoryLink);
					productExcelObj.setInventory(inventoryObj);
					}
					// //System.out.println("Inventory link " +inventoryLink);
					break;
					
				case 6:
					String inventoryStatus = cell.getStringCellValue();
					if(!StringUtils.isEmpty(inventoryStatus)){
					inventoryObj.setInventoryStatus(inventoryStatus);
					productExcelObj.setInventory(inventoryObj);
					}
					// //System.out.println("Inventory status " +inventoryStatus);
					break;
					
				case 7:
					int inventoryQuantity = (int) cell.getNumericCellValue();
					if(!StringUtils.isEmpty(Integer.toString(inventoryQuantity))){
					inventoryObj.setInventoryQuantity(Integer.toString(inventoryQuantity));
					productExcelObj.setInventory(inventoryObj);
					}
					// //System.out.println("Inventory quantity "
					// +inventoryQuantity);
					break;
					
				case 8:
					String description = cell.getStringCellValue();
					if(!StringUtils.isEmpty(description)){
					productExcelObj.setDescription(description);
					}else{
						productExcelObj.setDescription(ApplicationConstants.CONST_STRING_EMPTY);
					}
					// //System.out.println("product description is "
					// +description);
					break;
					
				case 9:
					String summary = cell.getStringCellValue();
					if(!StringUtils.isEmpty(summary)){
					productExcelObj.setSummary(summary);
					}else{
						productExcelObj.setSummary(ApplicationConstants.CONST_STRING_EMPTY);
					}
					//System.out.println("summary of product is " +summary);
					break;
				
				case 12:
					String category = cell.getStringCellValue();
					if(!StringUtils.isEmpty(category)){
					String categoryArr[] = category.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
					for (String string : categoryArr) {
						categories.add(string);
					}
					productExcelObj.setCategories(categories);
					}
					//System.out.println(columnIndex + "Category " + categories);
					break;

				case 13:
					String productKeyword = cell.getStringCellValue();
					if(!StringUtils.isEmpty(productKeyword)){
					String productKeywordArr[] = productKeyword.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
					for (String string : productKeywordArr) {
						productKeywords.add(string);
					}
					productExcelObj.setProductKeywords(productKeywords);
					//System.out.println(columnIndex + "Keyword "
						//	+ productKeywords);
					}
					break;
					
				case 14:
					
					String colorValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(colorValue)){
					color=colorparser.getColorCriteria(colorValue);
					if(color!=null){
					productConfigObj.setColors(color);
					}
					}
					break;
					
					
				case 16:
					 sizeGroup = cell.getStringCellValue();	
					 //System.out.println("case 16");
				break;
				
				case 17:
					
					String sizeValue = cell.getStringCellValue();
					//System.out.println("case 17");
					sizeObj=sizeParser.getSizes(sizeGroup,sizeValue);
					productConfigObj.setSizes(sizeObj);
					//System.out.println("Size Info is  "  +sizeObj);
					
				break;
				
				 case 18:
					String shapeValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(shapeValue)){
					shapeList=shapeParser.getShapeCriteria(shapeValue);
					if(shapeList!=null){
					productConfigObj.setShapes(shapeList);
					}
					}
					break;
					
				case 19:
					String themeValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(themeValue)){
					themes=themeParser.getThemeCriteria(themeValue);
					if(themes!=null){
					productConfigObj.setThemes(themes);
					}
					}
					//System.out.println(columnIndex + "Theme " + themes);
					break;
					
					case 20:
					String tradeValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(tradeValue)){
					tradeName=tradeNameParser.getTradeNameCriteria(tradeValue);
					if(tradeName!=null){
					productConfigObj.setTradeNames(tradeName);
					}
					}
					//System.out.println(columnIndex + "tradeName " + tradeName);
					break;
					
				case 21:
					String originValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(originValue)){
					origin=originParser.getOriginCriteria(originValue);
					if(origin!=null){
					productConfigObj.setOrigins(origin);
					}
					}
					//System.out.println("case 21");
					
					break;
					
				case 28:
					String imprintValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(imprintValue)){
					imprintMethods=imprintMethodParser.getImprintCriteria(imprintValue);
					if(imprintMethods!=null){
					productConfigObj.setImprintMethods(imprintMethods);
					}
					}
					//System.out.println(columnIndex + "imprintMethods " + imprintMethods);
					break;
					
				case 29:
					String lineName = cell.getStringCellValue();
					if(!StringUtils.isEmpty(lineName)){
					String lineNameArr[] = lineName.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
					for (String string : lineNameArr) {
						lineNames.add(string);
					}
					productExcelObj.setLineNames(lineNames);
					}
					
					break;
					
				case 30:
					String artwork = cell.getStringCellValue();
					if(!StringUtils.isEmpty(artwork)){
					artworkList=artworkProcessor.getArtworkCriteria(artwork);
					if(artworkList!=null){
					productConfigObj.setArtwork(artworkList);
					}}
					break;
					
				case 33:
					String persnlization = cell.getStringCellValue();
					if(!StringUtils.isEmpty(persnlization)){
					personalizationlist = personalizationParser.getPersonalization(persnlization);
					productConfigObj.setPersonalization(personalizationlist);
					//System.out.println("case 33");
					}
					break;
					
					
				case 38:
					prodSample = cell.getStringCellValue();
					////System.out.println(columnIndex + "prodSample " + prodSample);
					break;
					
				case 39:
					String specSample  = cell.getStringCellValue();
					boolean flag=false;
					if(!StringUtils.isEmpty(specSample)){
						flag=true;
					}
					samples=sampleParser.getSampleCriteria(prodSample, specSample,flag);
					if(samples!=null){
					productConfigObj.setSamples(samples);
					}
					//System.out.println(columnIndex + "samples " + samples);
					break;
					
				case 40:
					String productionTime = cell.getStringCellValue();
					if(!StringUtils.isEmpty(productionTime)){
					productionTimeList=productionTimeParser.getProdTimeCriteria(productionTime);
					if(productionTimeList!=null){
					productConfigObj.setProductionTime(productionTimeList);
					}
					//System.out.println(columnIndex + "productionTimeList " + productionTimeList);
				}
					 
					break;	
					
					
				case 41:
					 rushService = cell.getStringCellValue();
					break;
					
				case 42:
					if(!StringUtils.isEmpty(rushService) && !rushService.equalsIgnoreCase(ApplicationConstants.CONST_CHAR_N)){
					String rushTimeValue = cell.getStringCellValue();
					rushTime=rushTimeParser.getRushTimeCriteria(rushTimeValue);
					if(rushTime!=null){
					productConfigObj.setRushTime(rushTime);
					//System.out.println(columnIndex + "rushTime " + rushTime);
					}}
					break;
				
				case 43:
					String sameDayService=cell.getStringCellValue();
					if(!StringUtils.isEmpty(sameDayService)){
						sameDayObj=sameDayParser.getSameDayRush(sameDayService);
						if(sameDayObj!=null){
						productConfigObj.setSameDayRush(sameDayObj);
						//System.out.println(columnIndex + "rushTime " + rushTime);
						}
						}
					break;
					
				case 44:
					String packagingValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(packagingValue)){
					packaging=packagingParser.getPackagingCriteria(packagingValue);
					if(packaging!=null){
					productConfigObj.setPackaging(packaging);
					//System.out.println(columnIndex + "packaging " + packaging);
					}
					}
					break;
				case 45:
					shippingitemValue = cell.getStringCellValue();
					break;
					
				case 46:
					shippingdimensionValue = cell.getStringCellValue();

					//System.out.println("case 46");
					break;
					
				case 47:
					String shippingWeightValue = cell.getStringCellValue();
				
					ShipingItem = shipinestmt.getShippingEstimates(
							shippingitemValue, shippingdimensionValue,
							shippingWeightValue);
					productConfigObj.setShippingEstimates(ShipingItem);
					//System.out.println("case 47");
					break;
					
				case 48:
					String shipperBillsBy = cell.getStringCellValue();
					if(!StringUtils.isEmpty(shipperBillsBy)){
					productExcelObj.setShipperBillsBy(cell.getStringCellValue());
					// //System.out.println("summary of product is "
					// +shipperBillsBy);
					}else{
						productExcelObj.setShipperBillsBy(ApplicationConstants.CONST_STRING_EMPTY);
					}
					break;
					
				case 49:
					String additionalShippingInfo = cell.getStringCellValue();
					if(!StringUtils.isEmpty(additionalShippingInfo)){
					productExcelObj.setAdditionalShippingInfo(additionalShippingInfo);
					//System.out.println(columnIndex + "ship info "+ additionalShippingInfo);
					}else{
						productExcelObj.setAdditionalShippingInfo(ApplicationConstants.CONST_STRING_EMPTY);	
					}
					//System.out.println("case 49");
					break;
					
				case 50:
					String canShipInPlainBox = cell.getStringCellValue();
					if(!StringUtils.isEmpty(canShipInPlainBox)){
					if (canShipInPlainBox.trim().equalsIgnoreCase(ApplicationConstants.CONST_CHAR_Y)) {
						productExcelObj.setCanShipInPlainBox(true);
					} else {
						productExcelObj.setCanShipInPlainBox(false);
					}
					}else{ productExcelObj.setCanShipInPlainBox(false);
					}
					// //System.out.println("ship plain box"
					// +cell.getStringCellValue());
					break;
					
				case 51:
					String complianceCert = cell.getStringCellValue();
					if(!StringUtils.isEmpty(complianceCert)){
					String complianceCertArr[] = complianceCert.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
					for (String string : complianceCertArr) {
						complianceCerts.add(string);
					} 
					productExcelObj.setComplianceCerts(complianceCerts);
					}
					//System.out.println(columnIndex + "compcert "+ complianceCerts);
					break;
					
				case 52:
					String productDataSheet = cell.getStringCellValue();
					if(!StringUtils.isEmpty(productDataSheet)){
					productExcelObj.setProductDataSheet(productDataSheet);
					}else{
						productExcelObj.setProductDataSheet(ApplicationConstants.CONST_STRING_EMPTY);
					}
					// //System.out.println("product data sheet is "
					// +productDataSheet);
					break;
					
				case 53:
					String safetyWarning = cell.getStringCellValue();
					if(!StringUtils.isEmpty(safetyWarning)){
					String safetyWarningsArr[] = safetyWarning.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
					for (String string : safetyWarningsArr) {
						safetyWarnings.add(string);
					} 
					productExcelObj.setSafetyWarnings(safetyWarnings);
					}
					//System.out.println(columnIndex + "safety warning "+ safetyWarnings);
					break;

				case 54:
					String additionalProductInfo = cell.getStringCellValue();
					if(!StringUtils.isEmpty(additionalProductInfo)){
					productExcelObj.setAdditionalProductInfo(additionalProductInfo);
					}else{
						productExcelObj.setAdditionalProductInfo(ApplicationConstants.CONST_STRING_EMPTY);
					}
					//System.out.println(columnIndex + "prod info "+ additionalProductInfo);
					break;

				case 55:
					String distributorOnlyComments = cell.getStringCellValue();
					if(!StringUtils.isEmpty(distributorOnlyComments)){
					productExcelObj.setDistributorOnlyComments(distributorOnlyComments);
					}
					else{
						productExcelObj.setDistributorOnlyComments(ApplicationConstants.CONST_STRING_EMPTY);
					}
					//System.out.println(columnIndex + "distributor comments "+ distributorOnlyComments);
					break;

				case 56:
					String productDisclaimer = cell.getStringCellValue();
					if(!StringUtils.isEmpty(productDisclaimer)){
					productExcelObj.setProductDisclaimer(productDisclaimer);
					}
					else{
						productExcelObj.setProductDisclaimer(ApplicationConstants.CONST_STRING_EMPTY);
					} 
					//System.out.println(columnIndex + "productDisclaimer "+ productDisclaimer);
					break;
					
				case 94:
					//System.out.println(columnIndex + "canOrderLessThanMinimum "+ cell.getBooleanCellValue());
					if(!StringUtils.isEmpty(String.valueOf(cell.getBooleanCellValue()))){
					productExcelObj.setCanOrderLessThanMinimum(cell.getBooleanCellValue());
					}else
					{
						productExcelObj.setCanOrderLessThanMinimum(false);
					} 
					
					break;

				case 95:
					String priceType = cell.getStringCellValue();
					if(!StringUtils.isEmpty(priceType)){
						if(priceType.equalsIgnoreCase("List")){
					productExcelObj.setPriceType(ApplicationConstants.CONST_PRICE_TYPE_CODE_LIST);
						}else if(priceType.equalsIgnoreCase("Net")){
							productExcelObj.setPriceType(ApplicationConstants.CONST_PRICE_TYPE_CODE_NET);
						}
					}else{
						productExcelObj.setPriceType(ApplicationConstants.CONST_STRING_EMPTY);
					}
					//System.out.println(columnIndex + "priceType " + priceType);
					break;

				case 96:
					
					boolean breakOutByPrice = cell.getBooleanCellValue();
					if(!StringUtils.isEmpty(String.valueOf(breakOutByPrice))){
					productExcelObj.setBreakOutByPrice(breakOutByPrice);
					}else{
						productExcelObj.setBreakOutByPrice(false);	
					}
					// //System.out.println("Breakoutbyprice" +breakOutByPrice);
					break;
					
				case 134:
					String priceConfirmedThru = cell.getStringCellValue();
					productExcelObj.setPriceConfirmedThru(priceConfirmedThru);
					//System.out.println(columnIndex + "priceConfirmedThru "+ priceConfirmedThru);
					break;
					
				case 140:
				   SKUCriteria1 = cell.getStringCellValue();
					break;
					
				case 141:
				   SKUCriteria2 = cell.getStringCellValue();
					break;
					
				case 144:
					skuvalue = cell.getStringCellValue();
					break;
					
				case 145:
					Inlink = cell.getStringCellValue();
					break;
					
					
				case 146:
				    Instatus = cell.getStringCellValue();
					break;
					
				
				case 147:
					//int InQuantity= cell.getStringCellValue();
					int InQuantity = (int) cell.getNumericCellValue();
					skuObj=skuparserobj.getProductRelationSkus(SKUCriteria1, SKUCriteria2, skuvalue, Inlink, Instatus,Integer.toString(InQuantity));
					
					
					if(!StringUtils.isEmpty(skuObj.getSKU())){
						productsku.add(skuObj);	
					productExcelObj.setProductRelationSkus(productsku);
					}
					
					break;	
				case 148:
					String distributorViewOnly = cell.getStringCellValue();
					if(!StringUtils.isEmpty(distributorViewOnly) && distributorViewOnly.trim().equalsIgnoreCase(ApplicationConstants.CONST_CHAR_Y)) {
						productExcelObj.setSeoFlag(true);
						
					} else {
						productExcelObj.setSeoFlag(false);
					}
					// //System.out.println("distributorViewOnly "
					// +cell.getStringCellValue());
					break;
					
				case 149:
					String seoFlag = cell.getStringCellValue();
					if(!StringUtils.isEmpty(seoFlag) && seoFlag.trim().equalsIgnoreCase(ApplicationConstants.CONST_CHAR_Y)) {
						productExcelObj.setSeoFlag(true);
					} else {
						productExcelObj.setSeoFlag(false);
					}
					//System.out.println("case 149 " +cell.getStringCellValue());
					break;
				}
				
				productExcelObj.setProductConfigurations(productConfigObj);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				File json = new File("D:\\Excel Reader\\file.json");
			mapper.writeValue(json, productExcelObj);
			//	File json = new File("D:\\A4 ESPUpdate\\Excel File\\v2\\player.json");
			//mapper.writeValue(json, productExcelObj);
			//System.out.println("/////////////////////////////////////////");
			//System.out.println("Java object converted to JSON String, written to file");
			//System.out.println(mapper.writeValueAsString(productExcelObj)); 
			//System.out.println("/////////////////////////////////////////");
			} catch (JsonGenerationException ex) 
			{ ex.printStackTrace(); 
			} 
			catch (JsonMappingException ex) {
				ex.printStackTrace(); 
				} 
			catch (IOException ex)
			{ 
				ex.printStackTrace();
				
			}
			//product object when it is null;//do excption handling
			
		}catch(Exception e){
			//e.printStackTrace();
			_LOGGER.error("Error while Processing Product :"+productExcelObj.getExternalProductId() );
			
			 
		}
			productList.add(productExcelObj);
		workbook.close();
		inputStream.close();
		}
		}catch(Exception e){
			//e.printStackTrace();
			_LOGGER.error("Error while Processing excel sheet :"+path.substring(path.lastIndexOf("\\")) +e.getMessage());
			
			return null;
		}finally{
			try {
				workbook.close();
			inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				_LOGGER.error("Error while Processing excel sheet :"+path.substring(path.lastIndexOf("\\"))+e.getMessage());
			}
			
			
		}
		_LOGGER.info("Complted processing of excel sheet :"+path.substring(path.lastIndexOf("\\") ));
		_LOGGER.info("Total no of product:"+productList.size() );
		return productList;
	}
}
