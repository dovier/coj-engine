/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.text.DateFormatter;

public class Contest {
    //General
	private boolean balloon;
    private String even;
    //Create Contest
    private boolean serial;
    private int importCid;
    private boolean importData;
    
	//Manage Contest General
    private boolean enabled;
    private boolean locked;
    private boolean grouped;
    private int cid;
    private String name;
    private Date initdate;
    private Date rinitdate;
    private Date enddate;
    private Date rglimit;
    private int registration;
    private int registrationLimit;
    private int style;
    private int contestant;
    private String overview;
    //Global Settings
    private int frtime;
    private int deadtime;
    private int penalty;
    private int ioimark;
    private int ppoints;
    private int total_users;
    private int unfreeze_time;
    private int levels;
    private int acbylevels;
    private int aclimit;
    //Contest Flags
    private boolean show_status;
    private boolean show_scoreboard;
    private boolean allow_registration;
    private boolean unfreeze_auto;
    private boolean show_status_out;
    private boolean show_scoreboard_out;
    private boolean show_problem_out;
    private boolean show_ontest;
    private boolean blocked;
    private boolean repointing;
    //Contest Problems
    private String[] imports;
    //Views Variables
    private boolean running;
    private boolean past;
    private boolean coming;
    private boolean frozen;
    private boolean full_frozen;
    private String tostart;
    private Date now;
    private int gold;
    private int silver;
    private int bronze;
    private String elapsed;
    private String remaining;
    //Virtual Contest
    private String start_time;
    private String duration;
    private String idate;
    private boolean vtemplate;
    private List<Language> languages;
    private int[] currlanguages;
    private int[] problemids;
    private String[] problemcolors;
	private Object[] usersids;
    private int[] judgesids;
    private int ryear, rmonth, rday;
    private int rhour, rminutes, rseconds;
    private int iyear, imonth, iday;
    private int ihour, iminutes, iseconds;
    private int eyear, emonth, eday;
    private int ehour, eminutes, eseconds;
    private boolean virtual;
    private boolean is_public = true;
    private int vid;
    private String username;
    private String groupd;
    private String guestGroup;
    
    public String[] getProblemcolors() {
		return problemcolors;
	}

	public void setProblemcolors(String[] problemcolors) {
		this.problemcolors = problemcolors;
	}

    
    public String getGuestGroup() {
		return guestGroup;
	}

	public void setGuestGroup(String guestGroup) {
		this.guestGroup = guestGroup;
	}

	//s4ris
    private int from_start;

    public int getFrom_start() {
        return from_start;
    }

    	
    public boolean isGrouped() {
		return grouped;
	}

	public void setGrouped(boolean grouped) {
		this.grouped = grouped;
	}

	public void setFrom_start(int from_start) {
        this.from_start = from_start;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRepointing() {
        return repointing;
    }

    public void setRepointing(boolean repointing) {
        this.repointing = repointing;
    }

    public String getGroupd() {
        return groupd;
    }

    public void setGroupd(String groupd) {
        this.groupd = groupd;
    }

    public Date getRinitdate() {
        return rinitdate;
    }

    public void setRinitdate(Date rinitdate) {
        this.rinitdate = rinitdate;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public int getEday() {
        return eday;
    }

    public void setEday(int eday) {
        this.eday = eday;
    }

    public int getEhour() {
        return ehour;
    }

    public void setEhour(int ehour) {
        this.ehour = ehour;
    }

    public int getEminutes() {
        return eminutes;
    }

    public void setEminutes(int eminutes) {
        this.eminutes = eminutes;
    }

    public int getEmonth() {
        return emonth;
    }

    public void setEmonth(int emonth) {
        this.emonth = emonth;
    }

    public int getEseconds() {
        return eseconds;
    }

    public void setEseconds(int eseconds) {
        this.eseconds = eseconds;
    }

    public int getEyear() {
        return eyear;
    }

    public void setEyear(int eyear) {
        this.eyear = eyear;
    }

    public int getIday() {
        return iday;
    }

    public void setIday(int iday) {
        this.iday = iday;
    }

    public int getIhour() {
        return ihour;
    }

    public void setIhour(int ihour) {
        this.ihour = ihour;
    }

    public int getIminutes() {
        return iminutes;
    }

    public void setIminutes(int iminutes) {
        this.iminutes = iminutes;
    }

    public int getImonth() {
        return imonth;
    }

    public void setImonth(int imonth) {
        this.imonth = imonth;
    }

    public int getIseconds() {
        return iseconds;
    }

    public void setIseconds(int iseconds) {
        this.iseconds = iseconds;
    }

    public int getIyear() {
        return iyear;
    }

    public void setIyear(int iyear) {
        this.iyear = iyear;
    }

    public int getRday() {
        return rday;
    }

    public void setRday(int rday) {
        this.rday = rday;
    }

    public int getRhour() {
        return rhour;
    }

    public void setRhour(int rhour) {
        this.rhour = rhour;
    }

    public int getRminutes() {
        return rminutes;
    }

    public void setRminutes(int rminutes) {
        this.rminutes = rminutes;
    }

    public int getRmonth() {
        return rmonth;
    }

    public void setRmonth(int rmonth) {
        this.rmonth = rmonth;
    }

    public int getRseconds() {
        return rseconds;
    }

    public void setRseconds(int rseconds) {
        this.rseconds = rseconds;
    }

    public int getRyear() {
        return ryear;
    }

    public void setRyear(int ryear) {
        this.ryear = ryear;
    }

    public int[] getJudgesids() {
        return judgesids;
    }

    public void setJudgesids(int[] judgesids) {
        this.judgesids = judgesids;
    }

    public Object[] getUsersids() {
        return usersids;
    }

    public void setUsersids(Object[] usersids) {
        this.usersids = usersids;
    }

    public int[] getProblemids() {
        return problemids;
    }

    public void setProblemids(int[] problemids) {
        this.problemids = problemids;
    }

    public int[] getCurrlanguages() {
        return currlanguages;
    }

    public void setCurrlanguages(int[] currlanguages) {
        this.currlanguages = currlanguages;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public Contest() {
    }

    public Contest(int cid) {
        this.cid = cid;
    }

    public Contest(int cid, String name) {
        this.cid = cid;
        this.name = name;
    }

    public Contest(int cid, String name, String even) {
        this.cid = cid;
        this.name = name;
        this.even = even;
    }

    public Contest(int cid, String name, int registration, String even) {
        this.cid = cid;
        this.name = name;
        this.even = even;
        this.registration = registration;
    }

    public Contest(int cid, boolean serial, int importCid, String name) {
        this.cid = cid;
        this.serial = serial;
        this.importCid = importCid;
        this.name = name;
    }

    public Contest(int cid, String name, boolean enabled, int style, int registration, int contestant) {
        this.enabled = enabled;
        this.cid = cid;
        this.name = name;
        this.style = style;
        this.registration = registration;
        this.contestant = contestant;
    }

    public Contest(int cid, String name, boolean enabled, int style, int registration, int frtime, int deadtime, int penalty, int ioimark, int ppoints, int total_users, int unfreeze_time) {
        this.enabled = enabled;
        this.cid = cid;
        this.name = name;
        this.style = style;
        this.registration = registration;
        this.frtime = frtime;
        this.deadtime = deadtime;
        this.penalty = penalty;
        this.ioimark = ioimark;
        this.ppoints = ppoints;
        this.total_users = total_users;
        this.unfreeze_time = unfreeze_time;
    }

    public Contest(int cid, String name, boolean enabled, int style, int registration, int frtime, int deadtime, int penalty, int ioimark, int ppoints, int total_users, int unfreeze_time, int gold, int silver, int bronze) {
        this.enabled = enabled;
        this.cid = cid;
        this.name = name;
        this.style = style;
        this.registration = registration;
        this.frtime = frtime;
        this.deadtime = deadtime;
        this.penalty = penalty;
        this.ioimark = ioimark;
        this.ppoints = ppoints;
        this.total_users = total_users;
        this.unfreeze_time = unfreeze_time;
        this.gold = gold;
        this.bronze = bronze;
        this.silver = silver;
    }

    public Contest(int cid, String name, boolean enabled, int style, int registration, int frtime, int deadtime, int penalty, int ioimark, int ppoints, int total_users, int unfreeze_time, boolean blocked) {
        this.enabled = enabled;
        this.cid = cid;
        this.name = name;
        this.style = style;
        this.registration = registration;
        this.frtime = frtime;
        this.deadtime = deadtime;
        this.penalty = penalty;
        this.ioimark = ioimark;
        this.ppoints = ppoints;
        this.total_users = total_users;
        this.unfreeze_time = unfreeze_time;
        this.blocked = blocked;
    }

    public Contest(int cid, String name, boolean enabled, int style, int registration, int frtime, int deadtime, int penalty, int ioimark, int ppoints, int total_users, int unfreeze_time, boolean blocked, int gold, int silver, int bronze) {
        this.enabled = enabled;
        this.cid = cid;
        this.name = name;
        this.style = style;
        this.registration = registration;
        this.frtime = frtime;
        this.deadtime = deadtime;
        this.penalty = penalty;
        this.ioimark = ioimark;
        this.ppoints = ppoints;
        this.total_users = total_users;
        this.unfreeze_time = unfreeze_time;
        this.blocked = blocked;
        this.gold = gold;
        this.bronze = bronze;
        this.silver = silver;
    }

    public Contest(int cid, boolean serial, int importCid, boolean importData) {
        this.cid = cid;
        this.serial = serial;
        this.importCid = importCid;
        this.importData = importData;
    }

    public Contest(int cid, String name, Date initdate, Date enddate, Date rglimit) {
        this.cid = cid;
        this.name = name;
        this.initdate = initdate;
        this.enddate = enddate;
        this.rglimit = rglimit;
    }

    public Contest(int cid, String name, Date initdate, Date enddate, Date rglimit, int style, int registration, boolean enabled) {
        this.cid = cid;
        this.name = name;
        this.initdate = initdate;
        this.enddate = enddate;
        this.rglimit = rglimit;
        this.style = style;
        this.registration = registration;
        this.enabled = enabled;
    }

    public Contest(int cid, String name, Date initdate, Date enddate, Date rglimit, int style, int registration, boolean enabled, int contestant) {
        this.cid = cid;
        this.name = name;
        this.initdate = initdate;
        this.enddate = enddate;
        this.rglimit = rglimit;
        this.style = style;
        this.registration = registration;
        this.enabled = enabled;
        this.contestant = contestant;
    }

    public Contest(int cid, int style, int registration) {
        this.registration = registration;
        this.style = style;
        this.cid = cid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImportCid() {
        return importCid;
    }

    public void setImportCid(int importCid) {
        this.importCid = importCid;
    }

    public boolean isSerial() {
        return serial;
    }

    public void setSerial(boolean serial) {
        this.serial = serial;
    }

    public boolean isImportData() {
        return importData;
    }

    public void setImportData(boolean importData) {
        this.importData = importData;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
        this.eyear = enddate.getYear() + 1900;
        this.emonth = enddate.getMonth() + 1;
        this.eday = enddate.getDate();

        this.ehour = enddate.getHours();
        this.eminutes = enddate.getMinutes();
        this.eseconds = enddate.getSeconds();
    }

    public Date getInitdate() {
        return initdate;
    }

    public void setInitdate(Date initdate) {
        this.initdate = initdate;
        this.iyear = initdate.getYear() + 1900;
        this.imonth = initdate.getMonth() + 1;
        this.iday = initdate.getDate();

        this.ihour = initdate.getHours();
        this.iminutes = initdate.getMinutes();
        this.iseconds = initdate.getSeconds();
    }

    public Date getRglimit() {
        return rglimit;
    }

    public void setRglimit(Date rglimit) {
        this.rglimit = rglimit;
        this.ryear = rglimit.getYear() + 1900;
        this.rmonth = rglimit.getMonth() + 1;
        this.rday = rglimit.getDate();

        this.rhour = rglimit.getHours();
        this.rminutes = rglimit.getMinutes();
        this.rseconds = rglimit.getSeconds();
    }

    public String getEven() {
        return even;
    }

    public void setEven(String even) {
        this.even = even;
    }

    public int getRegistration() {
        return registration;
    }

    public void setRegistration(int registration) {
        this.registration = registration;
    }

    public int getRegistrationLimit() {
        return registrationLimit;
    }

    public void setRegistrationLimit(int registrationLimit) {
        this.registrationLimit = registrationLimit;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getDeadtime() {
        return deadtime;
    }

    public void setDeadtime(int deadtime) {
        this.deadtime = deadtime;
    }

    public int getFrtime() {
        return frtime;
    }

    public void setFrtime(int frtime) {
        this.frtime = frtime;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getIoimark() {
        return ioimark;
    }

    public void setIoimark(int ioimark) {
        this.ioimark = ioimark;
    }

    public int getPpoints() {
        return ppoints;
    }

    public void setPpoints(int ppoints) {
        this.ppoints = ppoints;
    }

    public boolean isShow_scoreboard() {
        return show_scoreboard;
    }

    public void setShow_scoreboard(boolean show_scoreboard) {
        this.show_scoreboard = show_scoreboard;
    }

    public boolean isShow_status() {
        return show_status;
    }

    public void setShow_status(boolean show_status) {
        this.show_status = show_status;
    }

    public int getTotal_users() {
        return total_users;
    }

    public void setTotal_users(int total_users) {
        this.total_users = total_users;
    }

    public boolean isAllow_registration() {
        return allow_registration;
    }

    public void setAllow_registration(boolean allow_registration) {
        this.allow_registration = allow_registration;
    }

    public int getUnfreeze_time() {
        return unfreeze_time;
    }

    public void setUnfreeze_time(int unfreeze_time) {
        this.unfreeze_time = unfreeze_time;
    }

    public boolean isUnfreeze_auto() {
        return unfreeze_auto;
    }

    public void setUnfreeze_auto(boolean unfreeze_auto) {
        this.unfreeze_auto = unfreeze_auto;
    }

    public String[] getImports() {
        return imports;
    }

    public void setImports(String[] imports) {
        this.imports = imports;
    }

 

    public boolean isShow_problem_out() {
        return show_problem_out;
    }

    public void setShow_problem_out(boolean show_problem_out) {
        this.show_problem_out = show_problem_out;
    }

    public boolean isShow_scoreboard_out() {
        return show_scoreboard_out;
    }

    public void setShow_scoreboard_out(boolean show_scoreboard_out) {
        this.show_scoreboard_out = show_scoreboard_out;
    }

    public boolean isShow_status_out() {
        return show_status_out;
    }

    public void setShow_status_out(boolean show_status_out) {
        this.show_status_out = show_status_out;
    }

    public boolean isComing() {
        return coming;
    }

    public void setComing(boolean coming) {
        this.coming = coming;
    }

    public boolean isPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getTostart() {
        return tostart;
    }

    public void setTostart(String tostart) {
        this.tostart = tostart;
    }

    private void TOstart() {
        Date date = new Date();
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isFull_frozen() {
        return full_frozen;
    }

    public void setFull_frozen(boolean full_frozen) {
        this.full_frozen = full_frozen;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getContestant() {
        return contestant;
    }

    public void setContestant(int contestant) {
        this.contestant = contestant;
    }

    public int getBronze() {
        return bronze;
    }

    public void setBronze(int bronze) {
        this.bronze = bronze;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void putTimeReferences() {
        if (initdate.compareTo(now) > 0) {
            this.running = false;
            this.past = false;
            this.coming = true;
        } else if (now.compareTo(initdate) >= 0 && now.compareTo(enddate) < 0) {
            this.running = true;
            this.coming = false;
            this.past = false;
        } else {
            this.running = false;
            this.coming = false;
            this.past = true;
        }
        //Putting Frozen Flag
        this.frozen = false;
        switch (this.style) {
            case 1: {
                long end = this.enddate.getTime();
                long rnow = this.now.getTime();
                long milliseconds = this.frtime * 60 * 1000;
                long millis = this.deadtime * 60 * 1000;
                if (rnow >= end - milliseconds && rnow < end && blocked) {
                    this.frozen = true;
                    if (rnow >= end - millis && rnow < end) {
                        this.full_frozen = true;
                    }
                } else if (rnow >= end && blocked) {
                    this.frozen = true;
                    this.full_frozen = true;
                }
                break;
            }
        }
    }

    public boolean unblock() {
        return blocked && frozen && past && unfreeze_auto && enddate.getTime() < now.getTime() - unfreeze_time * 60000;
    }

    public boolean isInFrozen(long millis) {
        if (millis >= this.getEnddate().getTime() - this.frtime * 60000) {
            return true;
        }
        return false;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIdate() {
        return idate;
    }

    public void setIdate(String idate) {
        this.idate = idate;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    private int getMinutes(int value) {
        if (value < 60) {
            return 0;
        }
        return value / 60;
    }

    private int getHours(int value) {
        if (value < 60 * 60) {
            return 0;
        }
        return value / (60 * 60);
    }

    public void putTimeInformation() {
        long end = this.enddate.getTime();
        long rnow = this.now.getTime();
        long milliseconds = this.frtime * 60 * 1000;
        long millis = this.deadtime * 60 * 1000;
        if (rnow >= end - millis && rnow < end) {
            this.full_frozen = true;
        }
        else if (rnow >= end - milliseconds && rnow < end) {
        	this.frozen = true;
        }
    }

    public boolean isVtemplate() {
        return vtemplate;
    }

    public void setVtemplate(boolean vtemplate) {
        this.vtemplate = vtemplate;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public int getAcbylevels() {
        return acbylevels;
    }

    public void setAcbylevels(int acbylevels) {
        this.acbylevels = acbylevels;
    }

    public int getAclimit() {
        return aclimit;
    }

    public void setAclimit(int aclimit) {
        this.aclimit = aclimit;
    }

    public boolean isShow_ontest() {
        return show_ontest;
    }

    public void setShow_ontest(boolean show_ontest) {
        this.show_ontest = show_ontest;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isImportAll() {
        for (int i = 0; i < imports.length; i++) {
            String string = imports[i];
            if (string.equals("all")) {
                return true;
            }
        }
        return false;
    }

    public void initdates() {
        rglimit = new Date(ryear - 1900, rmonth - 1, rday, rhour, rminutes, rseconds);
        initdate = new Date(iyear - 1900, imonth - 1, iday, ihour, iminutes, iseconds);
        enddate = new Date(eyear - 1900, emonth - 1, eday, ehour, eminutes, eseconds);
    }

    public void initIDate() {
        initdate = new Date(iyear - 1900, imonth - 1, iday, ihour, iminutes, iseconds);
    }

    public boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }
    public boolean isBalloon() {
		return balloon;
	}

	public void setBalloon(boolean balloon) {
		this.balloon = balloon;
	}

}
