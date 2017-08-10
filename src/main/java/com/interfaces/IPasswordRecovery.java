package com.interfaces;

import com.entity.PasswordRecovery;

public interface IPasswordRecovery {
	public void create(PasswordRecovery pr);
	public PasswordRecovery retrieve(String token);
	public void update(PasswordRecovery pr);
	public void delete(PasswordRecovery pr);
}
