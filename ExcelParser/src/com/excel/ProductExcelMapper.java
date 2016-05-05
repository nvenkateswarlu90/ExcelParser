package com.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.util.StringUtils;

import com.a4.product.beans.Artwork;
import com.a4.product.beans.Color;
import com.a4.product.beans.ImprintMethod;
import com.a4.product.beans.Inventory;
import com.a4.product.beans.Personalization;
import com.a4.product.beans.PriceGrid;
import com.a4.product.beans.Product;
import com.a4.product.beans.ProductConfigurations;
import com.a4.product.beans.ProductSkus;
import com.a4.product.beans.ProductionTime;
import com.a4.product.beans.RushTime;
import com.a4.product.beans.SameDayRush;
import com.a4.product.beans.Samples;
import com.a4.product.beans.Shape;
import com.a4.product.beans.ShippingEstimate;
import com.a4.product.beans.Size;
import com.a4tech.product.lookupData.LookupData;
import com.criteria.parser.PersonlizationParser;
import com.criteria.parser.PriceGridParser;
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
import com.criteria.parser.ProductSkuParser;
import com.criteria.parser.ProductThemeParser;
import com.criteria.parser.ProductTradeNameParser;
import com.criteria.parser.ProductionTimeParser;
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
		List<Product> BeanObj = djvsd.readFileUsingPOI("D:\\Excel Reader\\productv2.xlsx");
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
		FileInputStream inputStream = null;
		Workbook workbook = null;
		List<String>  productXids = new ArrayList<String>();
		  Product productExcelObj = new Product();   
		  ProductConfigurations productConfigObj=new ProductConfigurations();
		  ProductSkuParser skuparserobj=new ProductSkuParser();
		  String externalProductId = null;
		  String currencyType = null;
		  String priceQurFlag = null;
		  String priceType    = null;
		  String basePriceName = null;
		  String priceIncludes = null;
		  PriceGridParser priceGridParser = new PriceGridParser();
		  String upChargeName = null;
		  String upChargeQur = null;
		  String upchargeType = null;
		  String upChargeDetails = null;
		  String upChargeLevel = null;
		  List<PriceGrid> priceGrids = new ArrayList<PriceGrid>();
		try{
			  inputStream = new FileInputStream(new File(path));
			_LOGGER.info("Completed Reading Excel file from File Path");
			_LOGGER.info("Creating & Initializing Workbook");
			  workbook = WorkbookFactory.create(new File(path));//new XSSFWorkbook(inputStream);
			_LOGGER.info("Workbook Object created");
			  //Product productExcelObj = new Product();
			  //ProductConfigurations productConfigObj=new ProductConfigurations();
	    _LOGGER.info("Processing WorkSheet");
	    Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		_LOGGER.info("Started Processing Product");
		StringBuilder listOfQuantity = new StringBuilder();
		StringBuilder listOfPrices = new StringBuilder();
		StringBuilder listOfDiscount = new StringBuilder();
		StringBuilder basePriceCriteria =  new StringBuilder();
		StringBuilder UpCharQuantity = new StringBuilder();
		StringBuilder UpCharPrices = new StringBuilder();
		StringBuilder UpCharDiscount = new StringBuilder();
		StringBuilder UpCharCriteria = new StringBuilder();
		String quantity = null;
		//StringBuilder UpCharCriteria2 = new StringBuilder();
		String SKUCriteria1 =null;
		String SKUCriteria2 =null;
		String skuvalue  =null;
		String Inlink  =null;
		String Instatus  =null;
		String InQuantity=null;
		ProductSkus skuObj= new ProductSkus();
		List<ProductSkus> productsku=new ArrayList<ProductSkus>();
		
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
			
			
			
			
		
			
			String shippingitemValue = null;
			String shippingdimensionValue = null;
			String sizeGroup=null;
			String rushService=null;
			String prodSample=null;
			
			 productXids.add(externalProductId);
			 
			
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				int columnIndex = cell.getColumnIndex();
				if(productXids.size() > 2 && !LookupData.isRepeateIndex(String.valueOf(columnIndex+1))){
					continue;
				}

				switch (columnIndex + 1) {
				case 1:
					 externalProductId = cell.getStringCellValue();
					_LOGGER.info("Processing Product :"+ externalProductId);
					_LOGGER.info("Processing Product :"+ externalProductId);
					/*if(externalProductId==null || externalProductId.isEmpty()){
						rowFlag=true;
						break;
					}*/
					productExcelObj.setExternalProductId(externalProductId);
					// //System.out.println("external id is " +externalProductId);
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
					break;

				case 13:
					String productKeyword = cell.getStringCellValue();
					if(!StringUtils.isEmpty(productKeyword)){
					String productKeywordArr[] = productKeyword.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
					for (String string : productKeywordArr) {
						productKeywords.add(string);
					}
					productExcelObj.setProductKeywords(productKeywords);
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
				break;
				
				case 17:
					
					String sizeValue = cell.getStringCellValue();
					sizeObj=sizeParser.getSizes(sizeGroup,sizeValue);
					productConfigObj.setSizes(sizeObj);
					
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
					}
					break;
					
					
				case 38:
					prodSample = cell.getStringCellValue();
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
					if(!StringUtils.isEmpty(shipperBillsBy.trim())){
					productExcelObj.setShipperBillsBy(cell.getStringCellValue());
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
						safetyWarnings.add(string.trim());
					} 
					productExcelObj.setSafetyWarnings(safetyWarnings);
					}
					break;

				case 54:
					String additionalProductInfo = cell.getStringCellValue();
					if(!StringUtils.isEmpty(additionalProductInfo)){
					productExcelObj.setAdditionalProductInfo(additionalProductInfo);
					}else{
						productExcelObj.setAdditionalProductInfo(ApplicationConstants.CONST_STRING_EMPTY);
					}
					break;

				case 55:
					String distributorOnlyComments = cell.getStringCellValue();
					if(!StringUtils.isEmpty(distributorOnlyComments)){
					productExcelObj.setDistributorOnlyComments(distributorOnlyComments);
					}
					else{
						productExcelObj.setDistributorOnlyComments(ApplicationConstants.CONST_STRING_EMPTY);
					}
					break;

				case 56:
					String productDisclaimer = cell.getStringCellValue();
					if(!StringUtils.isEmpty(productDisclaimer)){
					productExcelObj.setProductDisclaimer(productDisclaimer);
					}
					else{
						productExcelObj.setProductDisclaimer(ApplicationConstants.CONST_STRING_EMPTY);
					} 
					break;
				case 57:
					basePriceName = cell.getStringCellValue();
					break;
				case 58:
					String criteria1 = cell.getStringCellValue();
					if(!StringUtils.isEmpty(criteria1)){
						basePriceCriteria.append(criteria1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
					}
					break;
				case 59:
					String criteria2 = cell.getStringCellValue();
					if(!StringUtils.isEmpty(criteria2)){
						basePriceCriteria.append(criteria2);
					}
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 66:
				case 67:
				case 68:
				case 69:
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						quantity = cell.getStringCellValue();
				         if(!StringUtils.isEmpty(quantity)){
				        	 listOfQuantity.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						int quantity1 = (int)cell.getNumericCellValue();
				         if(!StringUtils.isEmpty(quantity1)){
				        	 listOfQuantity.append(quantity1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else{
						
					}
			          break;
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
				case 76:
				case 77:
				case 78:
				case 79:       
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
					quantity = cell.getStringCellValue();
			         if(!StringUtils.isEmpty(quantity)){
			        	 listOfPrices.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
			         }
				}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					double quantity1 = (double)cell.getNumericCellValue();
			         if(!StringUtils.isEmpty(quantity1)){
			        	 listOfPrices.append(quantity1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
			         }
				}else{
					
				}
					            break;
				case 80:
				case 81:
				case 82:
				case 83:
				case 84:
				case 85:
				case 86:
				case 87:
				case 88:
				case 89:
					
					quantity = cell.getStringCellValue();
			         if(!StringUtils.isEmpty(quantity)){
			        	 listOfDiscount.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
			         }
					          break;
				case 91:
					   priceIncludes = cell.getStringCellValue();
					   break;
				case 93:
					 currencyType = cell.getStringCellValue();
				case 94:
					if(cell.getCellType() ==  Cell.CELL_TYPE_BOOLEAN){
						if(!StringUtils.isEmpty(String.valueOf(cell.getBooleanCellValue()))){
							productExcelObj.setCanOrderLessThanMinimum(cell.getBooleanCellValue());
							}else
							{
								productExcelObj.setCanOrderLessThanMinimum(false);
							} 
					}else{
						productExcelObj.setCanOrderLessThanMinimum(false);
					}
					
					
					break;

				case 95:
					 priceType = cell.getStringCellValue();
					if(!StringUtils.isEmpty(priceType)){
						if(priceType.equalsIgnoreCase("List")){
					productExcelObj.setPriceType(ApplicationConstants.CONST_PRICE_TYPE_CODE_LIST);
						}else if(priceType.equalsIgnoreCase("Net")){
							productExcelObj.setPriceType(ApplicationConstants.CONST_PRICE_TYPE_CODE_NET);
						}
					}else{
						productExcelObj.setPriceType(ApplicationConstants.CONST_STRING_EMPTY);
					}
					break;

				case 96:
					
					boolean breakOutByPrice = cell.getBooleanCellValue();
					if(!StringUtils.isEmpty(String.valueOf(breakOutByPrice))){
					productExcelObj.setBreakOutByPrice(breakOutByPrice);
					}else{
						productExcelObj.setBreakOutByPrice(false);	
					}
					break;
				case 97:
					upChargeName = cell.getStringCellValue();
					break;//upcharge name
				case 98:
					String upCriteria1= cell.getStringCellValue();
					if(!StringUtils.isEmpty(upCriteria1)){
						UpCharCriteria.append(upCriteria1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
					}
					break;//upcharge criteria_1
				case 99:
					String upCriteria2= cell.getStringCellValue();
					if(!StringUtils.isEmpty(upCriteria2)){
						UpCharCriteria.append(upCriteria2);
					}
					break;//upcharge criteria_2
				case 100:
					upchargeType = cell.getStringCellValue();
					break;//upcharge type
				case 101:
					upChargeLevel = cell.getStringCellValue();
					break;//upcharge level
					
				case 102:
				case 103:
				case 104:
				case 105:
				case 106:
				case 107:
				case 108:
				case 109:
				case 110:
				case 111:
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						quantity = cell.getStringCellValue();
				         if(!StringUtils.isEmpty(quantity)){
				        	 UpCharQuantity.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						int quantity1 = (int)cell.getNumericCellValue();
				         if(!StringUtils.isEmpty(quantity1)){
				        	 UpCharQuantity.append(quantity1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else{
						
					}
					 break; // upcharge quanytity
					
				case 112:
				case 113:
				case 114:
				case 115:
				case 116:
				case 117:
				case 118:
				case 119:
				case 120:
				case 121:
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						quantity = cell.getStringCellValue();
				         if(!StringUtils.isEmpty(quantity)){
				        	 UpCharPrices.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						int quantity1 = (int)cell.getNumericCellValue();
				         if(!StringUtils.isEmpty(quantity1)){
				        	 UpCharPrices.append(quantity1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else{
						
					}
					 break; // upcharge prices
				case 122:
				case 123:
				case 124:
				case 125:
				case 126:
				case 127:
				case 128:
				case 129:
				case 130:
				case 131:
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						quantity = cell.getStringCellValue();
				         if(!StringUtils.isEmpty(quantity)){
				        	 UpCharDiscount.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						int quantity1 = (int)cell.getNumericCellValue();
				         if(!StringUtils.isEmpty(quantity1)){
				        	 UpCharDiscount.append(quantity1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else{
						
					}
					 break; // upcharge discount
				case 132:
					upChargeDetails = cell.getStringCellValue();
					break;// upcharge details
				case 133:
					    upChargeQur = cell.getStringCellValue();
					break;// QUR Flag
				case 134:
					String priceConfirmedThru = cell.getStringCellValue();
					//mmddyy   //yymmdd
					 
					String strArr[]=priceConfirmedThru.split("/");
					priceConfirmedThru=strArr[2]+"/"+strArr[0]+"/"+strArr[1];
					priceConfirmedThru=priceConfirmedThru.replaceAll("/", "-");
					 
					priceConfirmedThru=priceConfirmedThru+"T00:00:00";
					 	productExcelObj.setPriceConfirmedThru(priceConfirmedThru);
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
					 InQuantity =  cell.getStringCellValue();
					
					break;	
				case 148:
					String distributorViewOnly = cell.getStringCellValue();
					if(!StringUtils.isEmpty(distributorViewOnly) && distributorViewOnly.trim().equalsIgnoreCase(ApplicationConstants.CONST_CHAR_Y)) {
						productExcelObj.setSeoFlag(true);
						
					} else {
						productExcelObj.setSeoFlag(false);
					}
					break;
					
				case 149:
					String seoFlag = cell.getStringCellValue();
					if(!StringUtils.isEmpty(seoFlag) && seoFlag.trim().equalsIgnoreCase(ApplicationConstants.CONST_CHAR_Y)) {
						productExcelObj.setSeoFlag(true);
					} else {
						productExcelObj.setSeoFlag(false);
					}
					break;
				}
				
				productExcelObj.setProductConfigurations(productConfigObj);
			} 
			
			}catch(Exception e){
			//e.printStackTrace();
			_LOGGER.error("Error while Processing Product :"+productExcelObj.getExternalProductId() );
			
			 
		}
			productList.add(productExcelObj);
			if((!listOfPrices.toString().isEmpty() && priceQurFlag == null) || (listOfPrices.toString().isEmpty() && priceQurFlag != null)){
				priceGrids = priceGridParser.getPriceGrids(listOfPrices.toString(), listOfQuantity.toString(), listOfDiscount.toString(), currencyType,
						priceIncludes, true, priceQurFlag, basePriceName,basePriceCriteria.toString(),priceGrids);	
			}
			 
				if(UpCharCriteria != null && !UpCharCriteria.toString().isEmpty()){
					priceGrids = priceGridParser.getUpchargePriceGrid(UpCharQuantity.toString(), UpCharPrices.toString(), UpCharDiscount.toString(), UpCharCriteria.toString(), 
							 upChargeQur, currencyType, upChargeName, upchargeType, upChargeLevel, new Integer(1), priceGrids);
				}
				
				if(!StringUtils.isEmpty(skuvalue)){
					skuObj=skuparserobj.getProductRelationSkus(SKUCriteria1, SKUCriteria2, skuvalue, Inlink, Instatus,InQuantity);
					productsku.add(skuObj);	
				//productExcelObj.setProductRelationSkus(productsku);
				}
				
				upChargeQur = null;
				UpCharCriteria = new StringBuilder();
				priceQurFlag = null;
				listOfPrices = new StringBuilder();
				UpCharPrices = new StringBuilder();
				UpCharDiscount = new StringBuilder();
				UpCharQuantity = new StringBuilder();
				skuvalue = null;
				Inlink = null;
				Instatus = null;
				InQuantity = null;
				SKUCriteria1 = null;
				SKUCriteria2 = null;
		}
		workbook.close();
		inputStream.close();
		
		productExcelObj.setPriceGrids(priceGrids);
		productExcelObj.setProductRelationSkus(productsku);
		productList.add(productExcelObj);
		ObjectMapper mapper = new ObjectMapper();
		try {
			File json = new File("D:\\Excel Reader\\file.json");
		mapper.writeValue(json, productExcelObj);
		System.out.println("/////////////////////////////////////////");
		System.out.println("Java object converted to JSON String, written to file");
		System.out.println(mapper.writeValueAsString(productExcelObj)); 
		System.out.println("/////////////////////////////////////////");
		} catch (JsonGenerationException ex) 
		{ ex.printStackTrace(); 
		} 
		catch (JsonMappingException ex) {
			ex.printStackTrace(); 
			} 
		catch (IOException ex)
		{ ex.printStackTrace();
		}
		//}
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
