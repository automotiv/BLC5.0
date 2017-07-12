package com.sptools.core.validator;

import org.broadleafcommerce.core.web.checkout.model.ShippingInfoForm;
import org.broadleafcommerce.core.web.checkout.validator.ShippingInfoFormValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component("blINShippingInfoFormValidator")
public class INShippingInfoFormValidator extends ShippingInfoFormValidator {

    @SuppressWarnings("rawtypes")
    public boolean supports(Class clazz) {
        return clazz.equals(ShippingInfoForm.class);
    }

    public void validate(Object obj, Errors errors) {
        super.validate(obj, errors);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postalCode", "postalCode.required");
    }
}
