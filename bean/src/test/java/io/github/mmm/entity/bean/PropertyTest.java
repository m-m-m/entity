package io.github.mmm.entity.bean;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.base.exception.ReadOnlyException;
import io.github.mmm.marshall.StandardFormat;
import io.github.mmm.marshall.StructuredTextFormat;
import io.github.mmm.property.Property;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.factory.PropertyFactoryManager;
import io.github.mmm.value.observable.ObservableEvent;
import io.github.mmm.value.observable.ObservableEventListener;

/// Abstract test case for testing sub-classes of [Property].
///
/// @param <V> type of the [value][Property#get()].
/// @param <P> type of the [Property].
public abstract class PropertyTest<V, P extends Property<V>> extends Assertions {

  private static final String PROPERTY_NAME = "name123";

  private final V exampleValue;

  private final Class<V> valueClass;

  private final Class<P> propertyClass;

  private final boolean createByPropertyClass;

  /// The constructor.
  ///
  /// @param exampleValue the example value used for testing. May not be `null`.
  /// @param propertyClass the [Class] reflecting the [Property] to test.
  public PropertyTest(V exampleValue, Class<P> propertyClass) {

    this(exampleValue, propertyClass, false);
  }

  /// The constructor.
  ///
  /// @param exampleValue the example value used for testing. May not be `null`.
  /// @param propertyClass the [Class] reflecting the [Property] to test.
  /// @param createByPropertyClass - `true` to create the property to test from the given `propertyClass`, `false` otherwise (to create from
  ///        [Class] of `exampleValue`).
  @SuppressWarnings("unchecked")
  public PropertyTest(V exampleValue, Class<P> propertyClass, boolean createByPropertyClass) {

    super();
    this.exampleValue = exampleValue;
    this.valueClass = (Class<V>) exampleValue.getClass();
    this.propertyClass = propertyClass;
    this.createByPropertyClass = createByPropertyClass;
  }

  /// Test via [PropertyFactoryManager#create(Class, String)].
  @Test
  public void testPropertyFromFactory() {

    // arrange
    // act
    P property = createProperty();
    // assert
    assertThat(property).isNotNull();
    assertThat(property).isInstanceOf(this.propertyClass);
    assertThat(property.getName()).isEqualTo(PROPERTY_NAME);
    assertThat(property.getValueClass()).isAssignableFrom(this.valueClass);
    assertThat(property.get()).isNull();
    assertThat(property.getSafe()).isEqualTo(property.getFallbackSafeValue());
    P readOnly = WritableProperty.getReadOnly(property);
    assertThat(readOnly.get()).isNull();
    ChangeListener<V> listener = new ChangeListener<>();
    assertThrows(ReadOnlyException.class, () -> {
      readOnly.addListener(listener);
    });
    property.addListener(listener);
    property.set(this.exampleValue);
    ObservableEvent<V> event = listener.event;
    assertThat(property).hasToString(expectedToString(property));
    assertThat(property.get()).isSameAs(this.exampleValue);
    assertThat(property.isReadOnly()).isEqualTo(false);
    assertThat(readOnly.isReadOnly()).isEqualTo(true);
    if (readOnly.isValueMutable()) {
      verifyReadOnlyValue(readOnly);
    } else {
      assertThat(readOnly.get()).isSameAs(this.exampleValue);
    }
    assertThat(event).isNotNull();
    assertThat(event.getOldValue()).isNull();
    assertThat(event.getValue()).isSameAs(this.exampleValue);
    assertThat(event.getObservable()).isSameAs(property);
    Object eventChangeType = event.getChange();
    assertThat(eventChangeType).isNull();
    assertThrows(ReadOnlyException.class, () -> {
      readOnly.set(null);
    });
    verifyJsonMarshalling(property);
  }

  private P createProperty() {

    PropertyFactoryManager propertyFactoryManager = PropertyFactoryManager.get();
    Class<P> propertyType = null;
    if (this.createByPropertyClass) {
      propertyType = this.propertyClass;
    }
    return propertyFactoryManager.create(propertyType, this.valueClass, PROPERTY_NAME);
  }

  private void verifyJsonMarshalling(P property) {

    // arrange
    StructuredTextFormat jsonFormat = StandardFormat.json();
    // act
    String json = jsonFormat.write(property);
    WritableProperty<V> property2 = createProperty();
    assertThat(property.isEqual(property2)).isFalse();
    jsonFormat.read(json, property2);
    // assert
    // we have mapped our property to JSON and verify that we have entirely recreated it from the JSON string.
    assertThat(property2.get()).isNotNull().isEqualTo(property.get());
    assertThat(property2.isEqual(property)).isTrue();
  }

  /// Verifies the [value][WritableProperty#get()] of a [read-only][WritableProperty#getReadOnly()] [Property] pointing to the example value. This method has to
  /// be overridden for tests on [mutable values][Property#isValueMutable()].
  ///
  /// @param readOnly the [read-only property view][WritableProperty#getReadOnly()].
  protected void verifyReadOnlyValue(P readOnly) {

    throw new IllegalStateException("Property has mutable value (" + readOnly.getValueClass().getName() + ") but test (" + getClass().getSimpleName()
        + ") does not override verifyReadOnlyValue!");
  }

  private String expectedToString(WritableProperty<V> property) {

    String valueAsString = expectedPropertyValueToString(property);
    return property.getClass().getSimpleName() + "[" + PROPERTY_NAME + "=" + valueAsString + "]";
  }

  /// @param property the [WritableProperty] to test.
  /// @return the [toString()][Object#toString()] representation of the [property value][WritableProperty#get()] for the expected
  ///         [toString()][WritableProperty#toString()] representation of the [WritableProperty].
  protected String expectedPropertyValueToString(WritableProperty<V> property) {

    return property.get().toString();
  }

  private static class ChangeListener<V> implements ObservableEventListener<V> {

    private ObservableEvent<V> event;

    @Override
    public void onEvent(ObservableEvent<V> changeEvent) {

      assert (this.event == null);
      this.event = changeEvent;
    }

  }

}
