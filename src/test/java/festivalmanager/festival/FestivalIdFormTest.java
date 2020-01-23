package festivalmanager.festival;

import festivalmanager.ticket.Sort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FestivalIdFormTest {
	@Test
	public void canConvertStringToSort() {
		FestivalIdForm campingForm = new FestivalIdForm(20L, "CAMPINGTICKET");

		assertEquals(campingForm.getSort(), Sort.CAMPINGTICKET);


		FestivalIdForm otherForm = new FestivalIdForm(20L, "ASDF");

		assertEquals(otherForm.getSort(), Sort.DAYTICKET);
	}

	@Test
	public void canGetId() {
		long id = 20;

		FestivalIdForm form = new FestivalIdForm(id, "CAMPINGTICKET");

		assertEquals(id, form.getId());
	}
}
