package br.com.rr.deslemtech.archi.view.fragment;

import br.com.rr.deslemtech.view.fragment.ConfigsFragment;
import br.com.rr.deslemtech.view.fragment.DetailsFragment;
import br.com.rr.deslemtech.view.fragment.OverviewFragment;

public enum Frags {

	OVERVIEW("overview", OverviewFragment.class),
	DETAILS("details", DetailsFragment.class),
	CONFIGS("configs", ConfigsFragment.class);

	private String name;
	private Class<? extends AbstractFragment> classFrag;

	private Frags(final String name, Class<? extends AbstractFragment> classFrag) {
		this.name = name;
		this.classFrag = classFrag;
	}

	public String getName() {
		return name;
	}
	public Class<? extends AbstractFragment> getClassFrag() {
		return classFrag;
	}
}
