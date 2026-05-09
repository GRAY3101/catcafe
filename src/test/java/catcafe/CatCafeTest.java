package catcafe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CatCafe - 10 Unit Tests")
class CatCafeTest {

  private CatCafe cafe;

  @BeforeEach
  void setUp() {
    // Given: Ein frisches Café vor jedem Test
    cafe = new CatCafe();
  }

  @Test
  @DisplayName("Sollte eine Katze erfolgreich hinzufügen")
  void addCat_newCat_shouldIncreaseSize() {
    // Given
    FelineOverLord cat = new FelineOverLord("Luna", 3);

    // When
    cafe.addCat(cat);

    // Then
    assertEquals(1, cafe.getCatCount());
  }

  @Test
  @DisplayName("Sollte Katzen mit gleichem Gewicht ignorieren")
  void addCat_duplicateWeight_shouldNotIncreaseSize() {
    // Given
    cafe.addCat(new FelineOverLord("Original", 5));

    // When: Eine andere Katze mit gleichem Gewicht hinzufügen
    cafe.addCat(new FelineOverLord("Imposter", 5));

    // Then
    assertEquals(1, cafe.getCatCount(), "Die Größe sollte bei gleichem Gewicht nicht steigen.");
    assertNotNull(cafe.getCatByName("Original"));
    assertNull(cafe.getCatByName("Imposter"), "Das Duplikat sollte nicht im Baum gelandet sein.");
  }

  @Test
  @DisplayName("Sollte die Anzahl bei unterschiedlichen Gewichten korrekt zählen")
  void getCatCount_multipleUniqueWeights_shouldBeCorrect() {
    // Given
    cafe.addCat(new FelineOverLord("Light", 2));
    cafe.addCat(new FelineOverLord("Medium", 4));
    cafe.addCat(new FelineOverLord("Heavy", 6));

    // When
    long count = cafe.getCatCount();

    // Then
    assertEquals(3, count);
  }

  @Test
  @DisplayName("Sollte eine Katze exakt nach Namen finden")
  void getCatByName_existingName_shouldReturnCorrectCat() {
    // Given
    cafe.addCat(new FelineOverLord("Morticia", 3));
    cafe.addCat(new FelineOverLord("Fitzby", 5));

    // When
    FelineOverLord found = cafe.getCatByName("Morticia");

    // Then
    assertNotNull(found);
    assertEquals("Morticia", found.name());
  }

  @Test
  @DisplayName("Sollte null zurückgeben, wenn der Name nicht existiert")
  void getCatByName_unknown_shouldReturnNull() {
    // Given
    cafe.addCat(new FelineOverLord("Luna", 3));

    // When
    FelineOverLord found = cafe.getCatByName("Garfield");

    // Then
    assertNull(found);
  }

  @Test
  @DisplayName("Sollte eine Katze im Gewichtsbereich finden")
  void getCatByWeight_inRange_shouldReturnCat() {
    // Given
    cafe.addCat(new FelineOverLord("Chonk", 10));

    // When
    FelineOverLord found = cafe.getCatByWeight(8, 12);

    // Then
    assertNotNull(found);
    assertEquals(10, found.weight());
  }

  @Test
  @DisplayName("Sollte untere Gewichtsgrenze inklusiv behandeln")
  void getCatByWeight_lowerBound_shouldBeInclusive() {
    // Given
    cafe.addCat(new FelineOverLord("Edge", 5));

    // When
    FelineOverLord found = cafe.getCatByWeight(5, 10);

    // Then
    assertNotNull(found, "5 sollte inklusive sein.");
  }

  @Test
  @DisplayName("Sollte obere Gewichtsgrenze exklusiv behandeln")
  void getCatByWeight_upperBound_shouldBeExclusive() {
    // Given
    cafe.addCat(new FelineOverLord("Edge", 5));

    // When
    FelineOverLord found = cafe.getCatByWeight(0, 5);

    // Then
    assertNull(found, "5 sollte exklusiv sein.");
  }

  @Test
  @DisplayName("Sollte null zurückgeben bei ungültigen Gewichtsbereichen")
  void getCatByWeight_invalidRange_shouldReturnNull() {
    // When
    FelineOverLord found = cafe.getCatByWeight(10, 5); // min > max

    // Then
    assertNull(found);
  }

  @Test
  @DisplayName("Sollte NullPointer werfen, wenn null-Katze hinzugefügt wird")
  void addCat_null_shouldThrowException() {
    // Given / When / Then
    assertThrows(NullPointerException.class, () -> cafe.addCat(null));
  }
}
