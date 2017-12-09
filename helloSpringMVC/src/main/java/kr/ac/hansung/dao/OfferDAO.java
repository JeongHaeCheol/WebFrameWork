package kr.ac.hansung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.model.Offer;


@Repository
public class OfferDAO {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired // Autowired 쓸때 beans.xml에서 annotation기능 반드시 활성화!!!!!!!!!!!
	public void setDataSource(DataSource dataSource) { 
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int getRowCount() {
		String sqlStatement = "select count(*) from offers";
	    return jdbcTemplate.queryForObject(sqlStatement, Integer.class);
	   
	}
	
	//query and return a single object
	public Offer getOffer(String name) {
		String sqlStatement = "select * from offers where name=?";
		
		//RowMapper.mapRow()는 결과를 객체에 저장해주는 역할을 한다. (mapRow메소드는 사용자가 구현해야한다.)
		return jdbcTemplate.queryForObject(sqlStatement, new Object[] {name},
				new RowMapper<Offer>(){
                        
					public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {

						Offer offer = new Offer();
						offer.setId(rs.getInt("id"));
						offer.setName(rs.getString("name"));
						offer.setEmail(rs.getString("email"));
						offer.setText(rs.getString("text"));
						
						return offer;
					}
			
		});
				
	}
	
	//query and return a multiple object
		public List<Offer> getOffers() {
			String sqlStatement = "select * from offers";
			
			//여러개의 객체를 query할때는 query메소드 사용
			return jdbcTemplate.query(sqlStatement, new RowMapper<Offer>(){
	                        
						public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {

							Offer offer = new Offer();
							offer.setId(rs.getInt("id"));
							offer.setName(rs.getString("name"));
							offer.setEmail(rs.getString("email"));
							offer.setText(rs.getString("text"));
							
							return offer;
						}
				
			});
					
		}
		
		public boolean insert(Offer offer) {
			String name = offer.getName();
			String email = offer.getEmail();
		    String text = offer.getText();
		    
		    String sqlStatement = "insert into offers (name, email,text) values(?,?,?)";
		    
		    // update 리턴값은 integer값 (몇개가 업데이트 됐는지)이기 때문에 == 1 조건을 리턴
		    return (jdbcTemplate.update(sqlStatement, new Object[]{name, email, text}) == 1 );
		}

		
		public boolean update(Offer offer) {
			
			int id = offer.getId();
			String name = offer.getName();
			String email = offer.getEmail();
		    String text = offer.getText();
		    
		    String sqlStatement = "update offers set name=?, email=?, text=? where id=?";
		    
		    // update 리턴값은 integer값 (몇개가 업데이트 됐는지)이기 때문에 == 1 조건을 리턴
		    return (jdbcTemplate.update(sqlStatement, new Object[]{name, email, text, id}) == 1 );
		}
		
		public boolean delete(int id) {
		    
		    String sqlStatement = "delete from offers where id=?";
		    
		    // update 리턴값은 integer값 (몇개가 업데이트 됐는지)이기 때문에 == 1 조건을 리턴
		    return (jdbcTemplate.update(sqlStatement, new Object[]{id}) == 1 );
		}
		
}
