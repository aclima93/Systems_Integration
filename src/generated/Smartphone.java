
package generated;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="technical_data" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="table" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="table_title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="table_data" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="data_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="data_value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="price" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *             &lt;minInclusive value="0"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="url" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="brand" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="currency" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="summary_data" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "technicalData"
})
@XmlRootElement(name = "smartphone")
public class Smartphone {

    @XmlElement(name = "technical_data")
    protected Smartphone.TechnicalData technicalData;
    @XmlAttribute(name = "price", required = true)
    protected BigDecimal price;
    @XmlAttribute(name = "url", required = true)
    protected String url;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "brand")
    protected String brand;
    @XmlAttribute(name = "currency", required = true)
    protected String currency;
    @XmlAttribute(name = "summary_data")
    protected String summaryData;

    /**
     * Gets the value of the technicalData property.
     * 
     * @return
     *     possible object is
     *     {@link Smartphone.TechnicalData }
     *     
     */
    public Smartphone.TechnicalData getTechnicalData() {
        return technicalData;
    }

    /**
     * Sets the value of the technicalData property.
     * 
     * @param value
     *     allowed object is
     *     {@link Smartphone.TechnicalData }
     *     
     */
    public void setTechnicalData(Smartphone.TechnicalData value) {
        this.technicalData = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the brand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the value of the brand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrand(String value) {
        this.brand = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the summaryData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryData() {
        return summaryData;
    }

    /**
     * Sets the value of the summaryData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryData(String value) {
        this.summaryData = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="table" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="table_title" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="table_data" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="data_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="data_value" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "table"
    })
    public static class TechnicalData {

        @XmlElement(required = true)
        protected List<Smartphone.TechnicalData.Table> table;

        /**
         * Gets the value of the table property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the table property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTable().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Smartphone.TechnicalData.Table }
         * 
         * 
         */
        public List<Smartphone.TechnicalData.Table> getTable() {
            if (table == null) {
                table = new ArrayList<Smartphone.TechnicalData.Table>();
            }
            return this.table;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="table_title" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="table_data" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="data_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="data_value" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "tableTitle",
            "tableData"
        })
        public static class Table {

            @XmlElement(name = "table_title", required = true)
            protected String tableTitle;
            @XmlElement(name = "table_data", required = true)
            protected List<Smartphone.TechnicalData.Table.TableData> tableData;

            /**
             * Gets the value of the tableTitle property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTableTitle() {
                return tableTitle;
            }

            /**
             * Sets the value of the tableTitle property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTableTitle(String value) {
                this.tableTitle = value;
            }

            /**
             * Gets the value of the tableData property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the tableData property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTableData().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Smartphone.TechnicalData.Table.TableData }
             * 
             * 
             */
            public List<Smartphone.TechnicalData.Table.TableData> getTableData() {
                if (tableData == null) {
                    tableData = new ArrayList<Smartphone.TechnicalData.Table.TableData>();
                }
                return this.tableData;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="data_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="data_value" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "dataName",
                "dataValue"
            })
            public static class TableData {

                @XmlElement(name = "data_name", required = true)
                protected String dataName;
                @XmlElement(name = "data_value", required = true)
                protected String dataValue;

                /**
                 * Gets the value of the dataName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDataName() {
                    return dataName;
                }

                /**
                 * Sets the value of the dataName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDataName(String value) {
                    this.dataName = value;
                }

                /**
                 * Gets the value of the dataValue property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDataValue() {
                    return dataValue;
                }

                /**
                 * Sets the value of the dataValue property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDataValue(String value) {
                    this.dataValue = value;
                }

            }

        }

    }

}
