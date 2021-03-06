package com.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Categories;
import com.entity.Register;
import com.interfaces.IRegister;
import com.service.CategoriesService;

@Transactional
@Repository
public class RegisterDao implements IRegister {
	private final String rowsByRoot = "FROM Register r, Categories c WHERE r.household_id = :household_id AND ((c.household_id = r.household_id AND "
			+ "c.category = r.category AND c.parent = :parent) or deposit > 0) group by entry_id";
	
	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void create(Register register) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(register);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Register retrieve(int entry_id) {
		Session session = session();
		Register register = (Register) session.createCriteria(Register.class).add(Restrictions.idEq(entry_id))
				.uniqueResult();
		session.disconnect();

		return register;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> retieveList(int household_id, Date start, Date end, String parent) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hql;

		if (start == null) {
			start = sdf.parse("1970-01-01");
		}

		if (end == null) {
			end = sdf.parse("2099-12-31");
		}

		Session session = session();
		if ("root".compareTo(parent) == 0) {
			hql = "FROM Register r, Categories c WHERE r.household_id = :household_id AND ((r.trans_date BETWEEN :start AND :end "
					+ "AND  c.category = r.category AND c.parent = :parent) OR deposit > 0) group by entry_id";
		} else {
			hql = "FROM Register r, Categories c WHERE r.household_id = :household_id AND (c.household_id = r.household_id AND r.trans_date BETWEEN :start AND :end "
					+ "AND  c.category = r.category AND c.parent = :parent) group by entry_id";
		}

		List<Object[]> rList = session.createQuery(hql).setInteger("household_id", household_id).setDate("start", start)
				.setDate("end", end).setString("parent", parent).list();
		session.disconnect();

		return rList;
	}

	@Override
	public void update(Register register) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(register);
		tx.commit();
		session.disconnect();
	}

	@SuppressWarnings("unchecked")
	public void totalTransactions(int household_id, String parent) {
		Session session = session();
		String hqlList;
		double total;
		Transaction tx = session.beginTransaction();
		if ("root".compareTo(parent) == 0) {
			hqlList = rowsByRoot;
			total = 0;
		} else {
			hqlList = "FROM Register r, Categories c WHERE r.household_id = :household_id AND (c.household_id = r.household_id AND " +
					  "c.category = r.category AND c.parent = :parent) ORDER BY entry_id";
			Categories cat = categoriesService.retrieveCategoryByName(household_id, parent);
			total = cat.getAmount();
		}
		String sqlUpd = "UPDATE register SET running_balance = ((:total + deposit) - withdrawal) WHERE entry_id = :entry_id";
		List<Object[]> obj = session.createQuery(hqlList).setInteger("household_id", household_id)
				.setString("parent", parent).list();
		
		for (Object[] reg : obj) {
			session.createSQLQuery(sqlUpd).setDouble("total", total).setInteger("entry_id", ((Register) reg[0]).getEntry_id()).executeUpdate();
			total += (((Register) reg[0]).getDeposit() - ((Register) reg[0]).getWithdrawal());
		}
		tx.commit();
		session.disconnect();
	}

	@SuppressWarnings("unchecked")
	public double getTotalWithdrawals(int household_id, String parent) {
		Session session = session();
		double withdrawal = 0;
		String hql = "FROM Register r, Categories c WHERE r.household_id = :household_id AND (c.household_id = r.household_id AND " +
					 "c.category = r.category AND c.parent = :parent)";
		List<Register> r = session.createQuery(hql).setInteger("household_id", household_id).setString("parent", parent)
				.list();

		if (r.size() > 0) {
			String wHql = "SELECT SUM(withdrawal) FROM register r, categories c WHERE r.household_id = :household_id AND c.household_id = r.household_id " +
						  "AND c.category = r.category AND c.parent = :parent";
			withdrawal = (double) session.createSQLQuery(wHql).setInteger("household_id", household_id)
					.setString("parent", parent).uniqueResult();
		}
		session.disconnect();

		return withdrawal;
	}

	@SuppressWarnings("unchecked")
	public double getTotalDeposits(int household_id) {
		Session session = session();
		double deposit = 0;
		String hql = "FROM Register WHERE household_id = :household_id AND deposit > 0";
		List<Register> r = session.createQuery(hql).setInteger("household_id", household_id).list();
		if (r.size() > 0) {
			String dHql = "SELECT SUM(deposit) FROM Register WHERE household_id = :household_id AND deposit > 0";
			deposit = (double) session.createQuery(dHql).setInteger("household_id", household_id).uniqueResult();
		}
		session.disconnect();

		return deposit;
	}

	public double getBalanceByCategory(int household_id, String category) {
		Session session = session();
		String hql = "SELECT (SUM(deposit) - SUM(withdrawal)) FROM Register WHERE household_id = :household_id AND (category = :category OR deposit > 0)";

		double balance = (double) session.createQuery(hql).setInteger("household_id", household_id)
				.setString("category", category).uniqueResult();
		session.disconnect();

		return balance;
	}

	public double getExpenseByCategory(int household_id, String category) {
		Session session = session();
		double ending_balance = 0;
		String hql = "SELECT SUM(withdrawal) FROM register WHERE household_id = :household_id AND category = :category";

		Object obj = session.createSQLQuery(hql).setInteger("household_id", household_id).setString("category", category).uniqueResult();

		if (obj != null) {
			ending_balance = (double) obj;
		}
		session.disconnect();

		return ending_balance;
	}
	
	@SuppressWarnings("unchecked")
	public double getExpenseByParent(int household_id, String parent) {
		double total = 0;
		
		Session session = session();
		String hql = "FROM Register r, Categories c WHERE r.household_id = " +
					 ":household_id AND (c.category = r.category AND c.parent = :parent) group by entry_id";
		List<Object[]> obj = session.createQuery(hql).setInteger("household_id", household_id).setString("parent", parent).list();
		session.disconnect();
		
		for (Object[] reg : obj) {
			total += ((Register) reg[0]).getWithdrawal();
		}
		
		return total;
	}
	
	public void archivePreviousMonth(int household_id, int month) {
		Session session = session();
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date trans_date = cal.getTime();
		
		double balance = totalMonth(session, household_id, month);

		Register lastRec = getLastRecord(session, household_id, month);
		//If no records exist from the previous month then we're done
		if (lastRec == null) {
			return;
		}
		lastRec.setDeposit(balance);
		lastRec.setWithdrawal(0);
		lastRec.setCategory("");
		lastRec.setRecipient("Previous Month");
		lastRec.setDescription("Closing Balance");
		lastRec.setTrans_date(trans_date);
		update(lastRec);
		
		Transaction tx = session.beginTransaction();
		removeLastMonth(session, month, household_id);
		tx.commit();
		session.disconnect();

	}
	
	private Register getLastRecord(Session session, int household_id, int month) {
		String hql = "FROM Register WHERE household_id = :household_id AND MONTH(trans_date) <= :month ORDER BY entry_id DESC";
		Register reg = (Register) session.createQuery(hql).setInteger("household_id", household_id).setInteger("month", month).setMaxResults(1).uniqueResult();
		
		return reg;
	}
	
	@SuppressWarnings("unchecked")
	private double totalMonth(Session session, int household_id, int month) {
		double total = 0;
		String hql = "FROM Register r, Categories c WHERE r.household_id = :household_id AND MONTH(trans_date) " +
					 "<= :month AND ((c.category = r.category AND c.parent = 'root') or deposit > 0) group by entry_id";
		
		List<Object[]> obj = session.createQuery(hql).setInteger("household_id", household_id).setInteger("month", month).list();
		
		for (Object[] reg : obj) {
			total += ((Register) reg[0]).getDeposit() - ((Register) reg[0]).getWithdrawal();
		}
		session.disconnect();
		return total;
	}
		
	private void removeLastMonth(Session session, int month, int household_id) {
		String hql = "DELETE FROM Register WHERE household_id = :household_id AND MONTH(trans_date) <= :month";
		session.createQuery(hql).setInteger("household_id", household_id).setInteger("month", month).executeUpdate();
	}
	public boolean hasTransactions(int household_id, int month) {
		Session session = session();
		String hql = "SELECT COUNT(*) FROM Register WHERE household_id = :household_id AND MONTH(trans_date) <= :month";
		
		Long count = (Long) session.createQuery(hql).setInteger("household_id", household_id).setInteger("month", month).uniqueResult();
		session.disconnect();
		return (count > 0);
	}
	public void removeTransactionsByCategory(int household_id, String category) {
		Session session = session();
		String hql = "DELETE FROM Register WHERE household_id = :household_id AND category = :category";
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setInteger("household_id", household_id).setString("category", category).executeUpdate();
		tx.commit();
		session.disconnect();
	}
	
	@Override
	public void delete(Register register) {
		Session session = session();
		String hql = "DELETE FROM Register WHERE entry_id = :entry_id";
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setInteger("entry_id", register.getEntry_id()).executeUpdate();
		tx.commit();
		session.disconnect();
	}

	@SuppressWarnings("unchecked")
	public List<Register> retrieveRawList(int household_id) {
		Session session = session();
		List<Register> regList = session.createCriteria(Register.class).add(Restrictions.eq("household_id", household_id)).list();
		session.disconnect();
		
		return regList;
	}

	public boolean transactionsExistByCategory(int household_id, String category) {
		Session session = session();
		String hql = "SELECT COUNT(*) FROM Register WHERE household_id = :household_id AND category = :category";
		
		long count = (long) session.createQuery(hql).setInteger("household_id", household_id).setString("category", category).uniqueResult();
		
		return (count > 0);
	}

	public long transactionCountByCategory(int household_id, String category) {
		Session session = session();
		String hql = "SELECT COUNT(*) FROM Register WHERE household_id = :household_id AND category = :category";
		
		long count = (long) session.createQuery(hql).setInteger("household_id", household_id).setString("category", category).uniqueResult();
		
		return count;
	}

	public void deleteChildren(int household_id, String parent) {
		Session session = session();
		String hql = "DELETE FROM Register WHERE household_id = :household_id AND category = :category";
		List<Categories> catList = categoriesService.retrieveRawList(household_id, parent);
		Transaction tx = session.beginTransaction();
		for (Categories cat : catList) {
			session.createQuery(hql).setInteger("household_id", household_id).setString("category", cat.getCategory()).executeUpdate();
		}
		tx.commit();
		session.disconnect();
		
	}

	@SuppressWarnings("unchecked")
	public List<Register> getTransactionsByMonth(int household_id, int month) {
		Session session = session();
		String hql = "FROM Register WHERE household_id = :household_id AND MONTH(trans_date) <= :month";
		List<Register> regList = session.createQuery(hql).setInteger("household_id", household_id).setInteger("month", month).list();
		
		session.disconnect();
		
		return regList;
	}

	public void deleteByHouseholdId(int household_id) {
		Session session = session();
		String hql = "DELETE FROM Register WHERE household_id = :household_id";
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setInteger("household_id", household_id).executeUpdate();
		tx.commit();
		session.disconnect();
	}

}
