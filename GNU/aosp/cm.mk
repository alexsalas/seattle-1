## Specify phone tech before including full_phone
$(call inherit-product, vendor/cm/config/gsm.mk)

# Release name
PRODUCT_RELEASE_NAME := msm7627a

# Inherit some common CM stuff.
$(call inherit-product, vendor/cm/config/common_full_phone.mk)

# Inherit device configuration
$(call inherit-product, device/unknown/msm7627a/device_msm7627a.mk)

## Device identifier. This must come after all inclusions
PRODUCT_DEVICE := msm7627a
PRODUCT_NAME := cm_msm7627a
PRODUCT_BRAND := unknown
PRODUCT_MODEL := msm7627a
PRODUCT_MANUFACTURER := unknown
