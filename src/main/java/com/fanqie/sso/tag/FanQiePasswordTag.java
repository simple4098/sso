package com.fanqie.sso.tag;

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
public class FanQiePasswordTag   extends FanQieInputTag {

    private boolean showPassword = false;


    /**
     * Is the password value to be rendered?
     * @return {@code true} if the password value to be rendered.
     */
    public boolean isShowPassword() {
        return this.showPassword;
    }

    /**
     * Is the password value to be rendered?
     * @param showPassword {@code true} if the password value is to be rendered.
     */
    public void setShowPassword(boolean showPassword) {
        this.showPassword = showPassword;
    }

    /**
     * Flags "type" as an illegal dynamic attribute.
     */
    @Override
    protected boolean isValidDynamicAttribute(String localName, Object value) {
        return !"type".equals(localName);
    }

    /**
     * Return '{@code password}' causing the rendered HTML '{@code input}'
     * element to have a '{@code type}' of '{@code password}'.
     */
    @Override
    protected String getType() {
        return "password";
    }

    /**
     * {@link #setShowPassword(boolean) 'showPassword'} property value is
     * {@link Boolean#TRUE true}.
     */
    @Override
    protected void writeValue(TagWriter tagWriter) throws JspException {
        if (this.showPassword) {
            super.writeValue(tagWriter);
        } else {
            tagWriter.writeAttribute("value", processFieldValue(getName(), "", getType()));
        }
    }
}
