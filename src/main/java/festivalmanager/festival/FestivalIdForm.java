package festivalmanager.festival;

import javax.validation.constraints.NotNull;

import festivalmanager.ticket.Sort;

public class FestivalIdForm {

    @NotNull
    private final Long id;
    @NotNull
    private final Sort sort;

	/**
	 *
	 * @param id: festival id
	 * @param sort_str: string representation of ticket sort
	 */
	public FestivalIdForm(Long id, String sort_str) {
      this.id = id;
      this.sort = stringToSort(sort_str);
    }

	/**
	 *
	 * @param string: string representation of ticket sort
	 * @return Sort object equivalent string parameter
	 */
	public Sort stringToSort(String string){
      if("CAMPINGTICKET".equals(string)){
        return Sort.CAMPINGTICKET;
      } else{
        return Sort.DAYTICKET;
      }
    }

	/**
	 *
	 * @return id of festival
	 */
	public Long getId() {
      return this.id;
    }

	/**
	 *
	 * @return ticket sort of festival
	 */
	public Sort getSort() {
      return this.sort;
    }
  }