
package generated2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="smartphone_overview" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="smartphone_url" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="brand" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="currency" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="price" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                       &lt;minInclusive value="0"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
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
    "smartphoneOverview"
})
@XmlRootElement(name = "search_smartphones")
public class SearchSmartphones {

    @XmlElement(name = "smartphone_overview", required = true)
    protected List<SearchSmartphones.SmartphoneOverview> smartphoneOverview;

    /**
     * Gets the value of the smartphoneOverview property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the smartphoneOverview property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSmartphoneOverview().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchSmartphones.SmartphoneOverview }
     * 
     * 
     */
    public List<SearchSmartphones.SmartphoneOverview> getSmartphoneOverview() {
        if (smartphoneOverview == null) {
            smartphoneOverview = new ArrayList<SearchSmartphones.SmartphoneOverview>();
        }
        return this.smartphoneOverview;
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
     *       &lt;attribute name="smartphone_url" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="brand" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="currency" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="price" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *             &lt;minInclusive value="0"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SmartphoneOverview {

        @XmlAttribute(name = "smartphone_url", required = true)
        protected String smartphoneUrl;
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "brand", required = true)
        protected String brand;
        @XmlAttribute(name = "currency", required = true)
        protected String currency;
        @XmlAttribute(name = "price", required = true)
        protected BigDecimal price;

        /**
         * Gets the value of the smartphoneUrl property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSmartphoneUrl() {
            return smartphoneUrl;
        }

        /**
         * Sets the value of the smartphoneUrl property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSmartphoneUrl(String value) {
            this.smartphoneUrl = value;
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

    }

}
