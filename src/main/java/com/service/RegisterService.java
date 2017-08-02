package com.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.dao.RegisterDao;
import com.entity.Categories;
import com.entity.Register;

import interfaces.IRegister;

@Service
public class RegisterService implements IRegister {

	@Autowired
	private RegisterDao registerDao;
	
	@Autowired
	private CategoriesService categoriesService;
	
	@Value("${app.files.archiveDir}")
	private String archiveDir;

	@Override
	public void create(Register register) {
		registerDao.create(register);
	}

	@Override
	public Register retrieve(int entry_id) {
		return registerDao.retrieve(entry_id);
	}
	
	public PagedListHolder<Register> retrieveList(int household_id, Date start, Date end, String parent) throws ParseException {
		List<Object[]> r = registerDao.retieveList(household_id, start, start, parent);
		List<Register> rl = new ArrayList<Register>();
		for (Object[] item : r) {
			rl.add((Register)item[0]);
		}

		return new PagedListHolder<Register>(rl);
	}
	public boolean hasSubCategories(int household_id, String parent) throws ParseException {
		List<Object[]> r = registerDao.retieveList(household_id, null, null, parent);
		
		return (r.size() > 0);
	}
	public List <Register> retrieveRawList(int household_id) {
		return registerDao.retrieveRawList(household_id);
	}
	public List<Categories> budgetBreakdown(int household_id, String parent) {
		double total = registerDao.getExpenseByParent(household_id, parent);
		List<Categories> catList = categoriesService.retrieveByParent(household_id, parent);
		
		for (Categories item : catList) {
			item.setAmount(registerDao.getExpenseByCategory(household_id, item.getCategory()));
			if (total > 0) {
				item.setPercentage((item.getAmount()/total) * 100);
			}else{
				item.setPercentage(0);
			}
		}
		
		return catList;
	}
	public void removeTransactionsByCategory(int household_id, String category) {
		registerDao.removeTransactionsByCategory(household_id, category);
	}

	@Override
	public void update(Register register) {
		registerDao.update(register);
	}

	@Override
	public void delete(Register register) {
		registerDao.delete(register);
	}
	public boolean transactionsExistByCategory(int household_id, String category) {
		return registerDao.transactionsExistByCategory(household_id, category);
	}
	public long transactioncountByCategory(int household_id, String category) {
		return registerDao.transactionCountByCategory(household_id, category);
	}
	public void totalTransaction(int household_id, String parent) {
		registerDao.totalTransactions(household_id, parent);
	}
	public double getEndingBalance(int household_id, String parent) {
		double deposits = registerDao.getTotalDeposits(household_id);
		double withdrawals = registerDao.getTotalWithdrawals(household_id, parent);
		
		return (deposits - withdrawals);
	}
	public double getExpenseByCategory(int household_id, String category) {
		
		return registerDao.getExpenseByCategory(household_id, category);
	}
	
	public void archiveBudget(int household_id, int month) throws IOException {
		String[] colNames = {"entry_id", "household_id", "trans_date", "recipient", "description", "withdrawal", "deposit", "category", "running_balance",};
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String filePath = archiveDir + sdf.format(cal.getTime()) + "-" + String.format("%08d", household_id) + ".csv";
		Writer archive = new FileWriter(filePath);
		CsvBeanWriter csvWriter = new CsvBeanWriter(archive, CsvPreference.STANDARD_PREFERENCE);
		
		List<Register> regList = registerDao.getTransactionsByMonth(household_id, month);
		for(Register reg : regList) {
			csvWriter.write(reg, colNames);
		}
		csvWriter.close();
		
		registerDao.archivePreviousMonth(household_id, month);
	}
	public boolean hasTransactions(int household_id, int month) {
		return registerDao.hasTransactions(household_id, month);
	}

	public void deleteChildren(int household_id, String parent) {
		registerDao.deleteChildren(household_id, parent);
	}
	public void deleteByHouseholdId(int household_id) {
		registerDao.deleteByHouseholdId(household_id);
	}
}
