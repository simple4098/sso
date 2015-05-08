package com.fanqie.sso.tag;

import org.springframework.web.servlet.tags.form.AbstractHtmlInputElementTag;
import org.springframework.web.servlet.tags.form.InputTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/5/7
 * @version: v1.0.0
 */
public class FanQieInputTag extends InputTag {

    public static final String SIZE_ATTRIBUTE = "size";

    public static final String MAXLENGTH_ATTRIBUTE = "maxlength";

    public static final String ALT_ATTRIBUTE = "alt";

    public static final String ONSELECT_ATTRIBUTE = "onselect";

    public static final String READONLY_ATTRIBUTE = "readonly";

    public static final String AUTOCOMPLETE_ATTRIBUTE = "autocomplete";
    public static final String PLACEHOLDER = "placeholder";

    public   String  placeholder ;

    private String size;

    private String maxlength;

    private String alt;

    private String onselect;

    private String autocomplete;


    /**
     * Set the value of the '{@code size}' attribute.
     * May be a runtime expression.
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Get the value of the '{@code size}' attribute.
     */
    protected String getSize() {
        return this.size;
    }

    /**
     * Set the value of the '{@code maxlength}' attribute.
     * May be a runtime expression.
     */
    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    /**
     * Get the value of the '{@code maxlength}' attribute.
     */
    protected String getMaxlength() {
        return this.maxlength;
    }

    /**
     * Set the value of the '{@code alt}' attribute.
     * May be a runtime expression.
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    /**
     * Get the value of the '{@code alt}' attribute.
     */
    protected String getAlt() {
        return this.alt;
    }

    /**
     * Set the value of the '{@code onselect}' attribute.
     * May be a runtime expression.
     */
    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    /**
     * Get the value of the '{@code onselect}' attribute.
     */
    protected String getOnselect() {
        return this.onselect;
    }

    /**
     * Set the value of the '{@code autocomplete}' attribute.
     * May be a runtime expression.
     */
    public void setAutocomplete(String autocomplete) {
        this.autocomplete = autocomplete;
    }

    /**
     * Get the value of the '{@code autocomplete}' attribute.
     */
    protected String getAutocomplete() {
        return this.autocomplete;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    /**
     * Writes the '{@code input}' tag to the supplied {@link org.springframework.web.servlet.tags.form.TagWriter}.
     * Uses the value returned by {@link #getType()} to determine which
     * type of '{@code input}' element to render.
     */
    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        tagWriter.startTag("input");

        writeDefaultAttributes(tagWriter);
        if (!hasDynamicTypeAttribute()) {
            tagWriter.writeAttribute("type", getType());
        }
        writeValue(tagWriter);

        // custom optional attributes
        writeOptionalAttribute(tagWriter, SIZE_ATTRIBUTE, getSize());
        writeOptionalAttribute(tagWriter, MAXLENGTH_ATTRIBUTE, getMaxlength());
        writeOptionalAttribute(tagWriter, ALT_ATTRIBUTE, getAlt());
        writeOptionalAttribute(tagWriter, ONSELECT_ATTRIBUTE, getOnselect());
        writeOptionalAttribute(tagWriter, AUTOCOMPLETE_ATTRIBUTE, getAutocomplete());
        writeOptionalAttribute(tagWriter, PLACEHOLDER, getPlaceholder());

        tagWriter.endTag();
        return SKIP_BODY;
    }

    private boolean hasDynamicTypeAttribute() {
        return getDynamicAttributes() != null && getDynamicAttributes().containsKey("type");
    }

    /**
     * Writes the '{@code value}' attribute to the supplied {@link TagWriter}.
     * Subclasses may choose to override this implementation to control exactly
     * when the value is written.
     */
    protected void writeValue(TagWriter tagWriter) throws JspException {
        String value = getDisplayString(getBoundValue(), getPropertyEditor());
        String type = hasDynamicTypeAttribute() ? (String) getDynamicAttributes().get("type") : getType();
        tagWriter.writeAttribute("value", processFieldValue(getName(), value, type));
    }

    /**
     * Flags {@code type="checkbox"} and {@code type="radio"} as illegal
     * dynamic attributes.
     */
    @Override
    protected boolean isValidDynamicAttribute(String localName, Object value) {
        if ("type".equals(localName)) {
            if ("checkbox".equals(value) || "radio".equals(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the value of the '{@code type}' attribute. Subclasses
     * can override this to change the type of '{@code input}' element
     * rendered. Default value is '{@code text}'.
     */
    protected String getType() {
        return "text";
    }

}
